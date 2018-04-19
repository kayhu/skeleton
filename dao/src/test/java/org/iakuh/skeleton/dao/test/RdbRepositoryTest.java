package org.iakuh.skeleton.dao.test;

import org.iakuh.skeleton.dao.rdb.config.MybatisConfig;
import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.repository.UserRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = MybatisConfig.class)
@Transactional
@Rollback
public class RdbRepositoryTest extends BaseTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testRdbRepository() {
    final String username = "iakuh";
    User user = new User();
    user.setUsername(username);
    user.setPassword("P@ssw0rd");
    user = userRepository.insertSelective(user);
    assertNotNull(user.getId());
  }
}
