package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.WatchedUpdateListener;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

@Setter
@Slf4j
public class ZookeeperGroup extends ConcurrentHashMap<String, Object> {

  private static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
  private static final int DEFAULT_BASE_SLEEP_TIME_MS = 3 * 1000;
  private static final int DEFAULT_MAX_RETRIES = 10;

  private String location;

  @Setter(AccessLevel.NONE)
  private transient CuratorFramework client;

  private String connectString;
  private String rootPath;
  private boolean ignoreDeletesFromSource = false;

  private int sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS;
  private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
  private int baseSleepTimeMs = DEFAULT_BASE_SLEEP_TIME_MS;
  private int maxRetries = DEFAULT_MAX_RETRIES;

  @PostConstruct
  public void init() throws Exception {
    log.info("Initializing ZookeeperGroup started");
    if (!StringUtils.isBlank(location)) {
      loadProperties();
      return;
    } else {
      loadZookeeper();
    }
    log.info("Initializing ZookeeperGroup finished");
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroy ZookeeperGroup started");
    if (client != null && client.getState().equals(CuratorFrameworkState.STARTED)) {
      client.close();
      client = null;
    }
    log.info("Destroy ZookeeperGroup finished");
  }

  private void loadProperties() throws IOException {
    Assert.notNull(location, "location cannot be null");
    
    log.info("Loading properties file {}", location);

    ClassPathResource resource = new ClassPathResource(location);

    Properties properties = PropertiesLoaderUtils
        .loadProperties(new EncodedResource(resource));

    properties.stringPropertyNames().stream()
        .forEach(key -> this.put(key, properties.getProperty(key)));

    log.info("Loading properties file finished");
  }
  
  private void loadZookeeper() throws Exception {
    Assert.notNull(connectString, "connectionString cannot be null");
    Assert.notNull(rootPath, "rootPath cannot be null");

    log.info("connectString: {}", connectString);
    log.info("rootPath: {}", rootPath);
    log.info("sessionTimeoutMs: {}", sessionTimeoutMs);
    log.info("connectionTimeoutMs: {}", connectionTimeoutMs);
    log.info("baseSleepTimeMs: {}", baseSleepTimeMs);
    log.info("maxRetries: {}", maxRetries);

    client = CuratorFrameworkFactory.builder()
        .connectString(connectString)
        .sessionTimeoutMs(sessionTimeoutMs)
        .connectionTimeoutMs(connectionTimeoutMs)
        .retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries))
        .build();

    client.start();
    client.getZookeeperClient().blockUntilConnectedOrTimedOut();

    ZooKeeperConfigurationSource source = new ZooKeeperConfigurationSource(client, rootPath);
    source.start();

    // A customized dynamic update for ZookeeperGroup
    CustomizedDynamicPropertyUpdater updater = new CustomizedDynamicPropertyUpdater(this);

    WatchedUpdateListener listener = new DynamicWatchedConfiguration(
        source, ignoreDeletesFromSource, updater);

    source.addUpdateListener(listener);
  }
}