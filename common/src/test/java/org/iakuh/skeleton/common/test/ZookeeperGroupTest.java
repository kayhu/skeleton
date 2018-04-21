package org.iakuh.skeleton.common.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
  private ZookeeperGroup zookeeperGroup;

  private static final String CONNECT_STRING_NAME = "zookeeper.config.connect.string";
  private static final String ROOT_PATH_NAME = "zookeeper.config.root.path";

  @Value("#{zookeeperGroup['test.test.test']}")
  private String valueAnnotatedField;

  private String rootPath;

  @Before
  public void setUp() throws Exception {
    rootPath = env.getProperty(ROOT_PATH_NAME);

    zookeeperClient.checkExists().creatingParentContainersIfNeeded()
        .forPath(rootPath + "/test.test.test");

    zookeeperClient.checkExists().creatingParentContainersIfNeeded()
        .forPath(rootPath + "/dynamic");
  }

  @Bean
  public ZookeeperGroup zookeeperGroup() throws IOException {
    ZookeeperGroup zkGroup = new ZookeeperGroup();
    zkGroup.setConnectString(env.getProperty(CONNECT_STRING_NAME));
    zkGroup.setRootPath(env.getProperty(ROOT_PATH_NAME));
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    zkGroup.setLocation(resolver.getResource(env.getProperty("zookeeper.config.location")));
    zkGroup.setOverwrite(true);
    return zkGroup;
  }

  @Bean
  public CuratorFramework zookeeperClient() throws InterruptedException {
    CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
        .connectString(env.getProperty(CONNECT_STRING_NAME))
        .retryPolicy(new ExponentialBackoffRetry(3000, 10)).build();

    curatorFramework.start();
    curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut();
    return curatorFramework;
  }

  @Test
  public void testValueAnnotatedField() {
    assertEquals("pass! pass! pass!", valueAnnotatedField);
  }

  @Test
  public void testPropertyAddedDeleted() throws Exception {
    zookeeperClient.create().creatingParentsIfNeeded()
        .forPath(rootPath + "/added", "1".getBytes("UTF-8"));
    TimeUnit.SECONDS.sleep(1);
    assertEquals("1", zookeeperGroup.get("added"));

    zookeeperClient.delete().forPath(rootPath + "/added");
    TimeUnit.SECONDS.sleep(1);
    assertNull(zookeeperGroup.get("added"));
  }

  @Test
  public void testPropertyChanged() throws Exception {
    zookeeperClient.setData().forPath(rootPath + "/dynamic", "1".getBytes("UTF-8"));
    TimeUnit.SECONDS.sleep(1);
    assertEquals("1", zookeeperGroup.get("dynamic"));

    zookeeperClient.setData().forPath(rootPath + "/dynamic", "2".getBytes("UTF-8"));
    TimeUnit.SECONDS.sleep(1);
    assertEquals("2", zookeeperGroup.get("dynamic"));
  }

  @After
  public void cleanUp() throws Exception {
    zookeeperClient.setData().forPath(
        rootPath + "/test.test.test", "pass! pass! pass!".getBytes("UTF-8"));
  }
}