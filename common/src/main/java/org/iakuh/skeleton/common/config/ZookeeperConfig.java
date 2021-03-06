package org.iakuh.skeleton.common.config;

import org.iakuh.skeleton.common.zookeeper.ZookeeperGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:common-config.properties")
public class ZookeeperConfig {

  @Value("${common.zookeeper.config.connect.string}")
  private String connectString;

  @Value("${common.zookeeper.config.root.path}")
  private String rootPath;

  @Bean
  public ZookeeperGroup zkGroup() {
    ZookeeperGroup zkGroup = new ZookeeperGroup();
    zkGroup.setConnectString(connectString);
    zkGroup.setRootPath(rootPath);
    return zkGroup;
  }
}
