package org.iakuh.skeleton.common.test;

import java.io.IOException;
import org.iakuh.skeleton.common.zookeeper.ZooKeeperGroup;
import org.iakuh.skeleton.test.BaseTest;
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
@ContextConfiguration(classes = ZooKeeperGroupTest.class)
public class ZooKeeperGroupTest extends BaseTest {

  @Autowired
  private Environment env;

  @Value("#{zkGroup['test.test.test']}")
  private String valueAnnotatedField;

  @Bean
  public ZooKeeperGroup zkGroup() throws IOException {
    ZooKeeperGroup zkGroup = new ZooKeeperGroup();
    zkGroup.setConnectString(env.getProperty("zookeeper.config.connect.string"));
    zkGroup.setRootPath(env.getProperty("zookeeper.config.root.path"));
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    zkGroup.setLocation(resolver.getResource(env.getProperty("zookeeper.config.location")));
    zkGroup.setOverwrite(true);
    return zkGroup;
  }

  @Test
  public void testValueAnnotatedField() {
    assertEquals("pass! pass! pass!", valueAnnotatedField);
  }
}