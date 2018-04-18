package org.iakuh.skeleton.common.config;

import org.iakuh.skeleton.common.zookeeper.ZooKeeperGroup;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ZooKeeperConfig.class)
public class ZooKeeperConfigTest extends BaseTest {

  @Autowired
  private ZooKeeperGroup zkGroup;

  @Test
  public void testZkGroup() {
    assertNull(zkGroup.get("not.exist"));
  }
}