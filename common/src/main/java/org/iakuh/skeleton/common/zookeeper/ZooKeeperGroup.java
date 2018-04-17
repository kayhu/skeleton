package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;


@Slf4j
public class ZooKeeperGroup extends HashMap<String, Object> {

  private static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
  private static final int DEFAULT_BASE_SLEEP_TIME_MS = 3 * 1000;
  private static final int DEFAULT_MAX_RETRIES = 10;

  private CuratorFramework client;

  private String connectString;
  private String rootPath;
  private Resource location;
  private String fileEncoding;
  private boolean overwrite = false;

  private int sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS;
  private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
  private int baseSleepTimeMs = DEFAULT_BASE_SLEEP_TIME_MS;
  private int maxRetries = DEFAULT_MAX_RETRIES;

  @PostConstruct
  public void init() throws Exception {

    Assert.notNull(connectString, "connectionString is null");
    Assert.notNull(rootPath, "rootPath is null");

    log.debug("Initializing ZooKeeperGroup");
    log.debug("connectString: {}", connectString);
    log.debug("rootPath: {}", rootPath);
    log.debug("sessionTimeoutMs: {}", sessionTimeoutMs);
    log.debug("connectionTimeoutMs: {}", connectionTimeoutMs);
    log.debug("baseSleepTimeMs: {}", baseSleepTimeMs);
    log.debug("maxRetries: {}", maxRetries);

    try {
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

      zkCfgSrc.start();

      // customized dynamic property updater
      CustomizedDynamicPropertyUpdater updater =
          new CustomizedDynamicPropertyUpdater(this);

      DynamicWatchedConfiguration zkDynamicConfig =
          new DynamicWatchedConfiguration(zkCfgSrc, false, updater);
      ConfigurationManager.install(zkDynamicConfig);

      // load properties file last
      this.loadProperties();

    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
    log.debug("Initializing ZooKeeperGroup done");
  }

  @PreDestroy
  public void destroy() {
    log.debug("Destroy ZooKeeperGroup");
    if (client != null && client.getState().equals(CuratorFrameworkState.STARTED)) {
      client.close();
      client = null;
    }
    log.debug("Destroy ZooKeeperGroup done");
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

  public String getConnectString() {
    return connectString;
  }

  public void setConnectString(String connectString) {
    this.connectString = connectString;
  }

  public String getRootPath() {
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public Resource getLocation() {
    return location;
  }

  public void setLocation(Resource location) {
    this.location = location;
  }

  public String getFileEncoding() {
    return fileEncoding;
  }

  public void setFileEncoding(String fileEncoding) {
    this.fileEncoding = fileEncoding;
  }

  public boolean getOverwrite() {
    return overwrite;
  }

  /**
   * If overwrite zookeeper config by properties file, default true.
   */
  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }

  public int getSessionTimeoutMs() {
    return sessionTimeoutMs;
  }

  public void setSessionTimeoutMs(int sessionTimeoutMs) {
    this.sessionTimeoutMs = sessionTimeoutMs;
  }

  public int getConnectionTimeoutMs() {
    return connectionTimeoutMs;
  }

  public void setConnectionTimeoutMs(int connectionTimeoutMs) {
    this.connectionTimeoutMs = connectionTimeoutMs;
  }

  public int getBaseSleepTimeMs() {
    return baseSleepTimeMs;
  }

  public void setBaseSleepTimeMs(int baseSleepTimeMs) {
    this.baseSleepTimeMs = baseSleepTimeMs;
  }

  public int getMaxRetries() {
    return maxRetries;
  }

  public void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }
}