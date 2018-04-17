package org.iakuh.skeleton.common;

import org.iakuh.skeleton.common.config.ZooKeeperConfig;
import org.iakuh.skeleton.common.zookeeper.ZooKeeperGroup;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ZooKeeperConfig.class)
public class ZooKeeperConfigTest extends BaseTest {

  @Autowired
  private ZooKeeperGroup zooKeeperGroup;

  @Test
  public void testZooKeeperGroup() {
  }
}
