package org.iakuh.skeleton.common.test.config;

import org.iakuh.skeleton.common.config.ZooKeeperConfig;
import org.iakuh.skeleton.common.zookeeper.ZookeeperGroup;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ZooKeeperConfig.class)
public class ZooKeeperConfigTest extends BaseTest {

  @Autowired
  private ZookeeperGroup zkGroup;

  @Test
  public void testZkGroup() {
    assertNull(zkGroup.get("not.exist"));
  }
}
