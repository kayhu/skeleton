package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

@Setter
@Slf4j
public class ZookeeperGroup extends HashMap<String, Object> {

  private static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
  private static final int DEFAULT_BASE_SLEEP_TIME_MS = 3 * 1000;
  private static final int DEFAULT_MAX_RETRIES = 10;

  @Setter(AccessLevel.NONE)
  private CuratorFramework client;

  private String connectString;
  private String rootPath;
  private Resource location;
  private String fileEncoding;
  // If overwrite zookeeper config by properties file, default false.
  private boolean overwrite = false;

  private int sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS;
  private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
  private int baseSleepTimeMs = DEFAULT_BASE_SLEEP_TIME_MS;
  private int maxRetries = DEFAULT_MAX_RETRIES;

  @PostConstruct
  public void init() throws Exception {
    Assert.notNull(connectString, "connectionString is null");
    Assert.notNull(rootPath, "rootPath is null");

    log.debug("Initializing ZookeeperGroup");
    log.debug("connectString: {}", connectString);
    log.debug("rootPath: {}", rootPath);
    log.debug("sessionTimeoutMs: {}", sessionTimeoutMs);
    log.debug("connectionTimeoutMs: {}", connectionTimeoutMs);
    log.debug("baseSleepTimeMs: {}", baseSleepTimeMs);
    log.debug("maxRetries: {}", maxRetries);

    client = CuratorFrameworkFactory.builder()
        .connectString(connectString)
        .sessionTimeoutMs(sessionTimeoutMs)
        .connectionTimeoutMs(connectionTimeoutMs)
        .retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries))
        .build();

    client.start();

    client.getZookeeperClient().blockUntilConnectedOrTimedOut();

    ZooKeeperConfigurationSource zkCfgSrc =
        new ZooKeeperConfigurationSource(client, rootPath);

    // A customized dynamic update for ZookeeperGroup
    CustomizedDynamicPropertyUpdater updater =
        new CustomizedDynamicPropertyUpdater(this);

    DynamicWatchedConfiguration updateListener =
        new DynamicWatchedConfiguration(zkCfgSrc, false, updater);

    zkCfgSrc.addUpdateListener(updateListener);

    zkCfgSrc.start();

    // load properties file last
    this.loadProperties();
    log.debug("Initializing ZookeeperGroup done");
  }

  @PreDestroy
  public void destroy() {
    log.debug("Destroy ZookeeperGroup");
    if (client != null && client.getState().equals(CuratorFrameworkState.STARTED)) {
      client.close();
      client = null;
    }
    log.debug("Destroy ZookeeperGroup done");
  }

  private void loadProperties() throws IOException {
    if (location == null) {
      return;
    }

    log.debug("Loading from properties file {}, overwrite {}", location, overwrite);

    Properties properties = PropertiesLoaderUtils.loadProperties(
        new EncodedResource(location, this.fileEncoding));

    for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
      String key = (String) e.nextElement();
      if (overwrite) {
        this.put(key, properties.getProperty(key));
      } else {
        this.putIfAbsent(key, properties.getProperty(key));
      }
    }
  }
}