package org.iakuh.skeleton.common.test.zookeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.iakuh.skeleton.common.zookeeper.ZookeeperGroup;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@PropertySource("classpath:test-config.properties")
@ContextConfiguration(classes = ZookeeperGroupTest.class)
public class ZookeeperGroupTest extends BaseTest {

  @Autowired
  private Environment env;

  @Autowired
  private CuratorFramework zookeeperClient;

  @Autowired
  private ZookeeperGroup zookeeper;

  @Autowired
  private ZookeeperGroup local;

  private static final String CONNECT_STRING_NAME = "zookeeper.config.connect.string";
  private static final String ROOT_PATH_NAME = "zookeeper.config.root.path";

  @Value("#{zookeeper['test.test.test']}")
  private String valueAnnotationField;

  private String rootPath;
  private String testPath;
  private String dynamicPath;
  private String annotationPath;

  @Before
  public void setUp() throws Exception {
    rootPath = env.getProperty(ROOT_PATH_NAME);
    testPath = rootPath + "/test";
    dynamicPath = rootPath + "/dynamic";
    annotationPath = rootPath + "/test.test.test";

    Stat stat = zookeeperClient.checkExists().forPath(testPath);
    if (stat == null) {
      zookeeperClient.create().creatingParentContainersIfNeeded()
          .forPath(testPath, "pass".getBytes());
    }

    stat = zookeeperClient.checkExists().forPath(dynamicPath);
    if (stat != null) {
      zookeeperClient.delete().forPath(dynamicPath);
    }
  }

  @Bean("local")
  public ZookeeperGroup local() throws IOException {
    ZookeeperGroup zkGroup = new ZookeeperGroup();
    zkGroup.setLocation(env.getProperty("zookeeper.config.location"));
    return zkGroup;
  }

  @Bean("zookeeper")
  public ZookeeperGroup zookeeper() throws IOException {
    ZookeeperGroup zookeeperGroup = new ZookeeperGroup();
    zookeeperGroup.setConnectString(env.getProperty(CONNECT_STRING_NAME));
    zookeeperGroup.setRootPath(env.getProperty(ROOT_PATH_NAME));
    return zookeeperGroup;
  }

  @Bean("zookeeperClient")
  public CuratorFramework zookeeperClient() throws InterruptedException {
    CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
        .connectString(env.getProperty(CONNECT_STRING_NAME))
        .retryPolicy(new ExponentialBackoffRetry(3000, 10)).build();

    curatorFramework.start();
    curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut();
    return curatorFramework;
  }

  @Test
  public void testValueAnnotationField() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
    assertEquals("pass! pass! pass!", valueAnnotationField);
  }

  @Test
  public void testDynamic() throws Exception {
    // added
    zookeeperClient.create().creatingParentsIfNeeded().forPath(dynamicPath, "1".getBytes("UTF-8"));
    TimeUnit.SECONDS.sleep(1);
    assertEquals("1", zookeeper.get("dynamic"));

    // changed
    zookeeperClient.setData().forPath(dynamicPath, "2".getBytes("UTF-8"));
    TimeUnit.SECONDS.sleep(1);
    assertEquals("2", zookeeper.get("dynamic"));

    // deleted
    zookeeperClient.delete().forPath(dynamicPath);
    TimeUnit.SECONDS.sleep(1);
    assertNull(zookeeper.get("dynamic"));
  }

  @Test
  public void testLocalProperties() {
    assertEquals("local! local! local!", local.get("test.test.test"));
  }

  @After
  public void cleanUp() throws Exception {
    Stat stat = zookeeperClient.checkExists().forPath(annotationPath);
    if (stat == null) {
      zookeeperClient.create().creatingParentContainersIfNeeded()
          .forPath(annotationPath, "pass".getBytes());
    }
  }
}