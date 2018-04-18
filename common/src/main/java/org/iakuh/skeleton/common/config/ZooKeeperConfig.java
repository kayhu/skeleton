package org.iakuh.skeleton.common.config;

import org.iakuh.skeleton.common.zookeeper.ZooKeeperGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:common-config.properties")
public class ZooKeeperConfig {

  @Value("${common.zookeeper.config.connect.string}")
  private String connectString;

  @Value("${common.zookeeper.config.root.path}")
  private String rootPath;

  @Bean
  public ZooKeeperGroup zkGroup() {
    ZooKeeperGroup zkGroup = new ZooKeeperGroup();
    zkGroup.setConnectString(connectString);
    zkGroup.setRootPath(rootPath);
    return zkGroup;
  }
}
