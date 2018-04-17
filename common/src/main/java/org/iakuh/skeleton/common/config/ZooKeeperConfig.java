package org.iakuh.skeleton.common.config;

import org.iakuh.skeleton.common.zookeeper.ZooKeeperGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/common.properties")
public class ZooKeeperConfig {

  @Bean
  public ZooKeeperGroup zookeeperConfig(
      @Value("${zookeeper.config.connect.string}") String connectString,
      @Value("${zookeeper.config.root.path}") String rootPath) {
    ZooKeeperGroup zooKeeperGroup = new ZooKeeperGroup();
    zooKeeperGroup.setConnectString(connectString);
    zooKeeperGroup.setRootPath(rootPath);
    return zooKeeperGroup;
  }
}
