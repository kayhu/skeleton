package org.iakuh.skeleton.dao.test.rdb;

import org.iakuh.skeleton.dao.rdb.config.MybatisConfig;
import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.repository.UserRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = MybatisConfig.class)
@Transactional
@Rollback
public class UserRepositoryTest extends BaseTest {

  @Autowired
  private UserRepository userRepository;

  private Long userId;
  private final String username = "tester";
  private final String password = "P@ssw0rd";

  @Before
  public void setUp() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setEnabled(true);
    user.toString();
    userId = userRepository.insert(user).getId();
    assertNotNull(userId);
  }

  @Test
  public void testSelectByPrimaryKey() {
    User user = userRepository.selectByPrimaryKey(userId);
    assertEquals("tester", user.getUsername());
  }

  @Test
  public void testUpdateByPrimaryKeySelective() {
    User user = new User();
    user.setId(userId);
    user.setPassword(password);
    userRepository.updateByPrimaryKeySelective(user);

    User result = userRepository.selectByPrimaryKey(userId);
    assertEquals(username, result.getUsername());
    assertEquals(password, result.getPassword());
  }

  @Test
  public void testUpdateByPrimaryKey() {
    User result = userRepository.selectByPrimaryKey(userId);
    assertTrue(result.getEnabled());

    User user = new User();
    user.setId(userId);
    user.setUsername(username);
    user.setPassword(password);
    user.setEnabled(false);
    userRepository.updateByPrimaryKey(user);

    result = userRepository.selectByPrimaryKey(userId);
    assertFalse(result.getEnabled());
  }

  @After
  public void cleanUp() {
    int result = userRepository.deleteByPrimaryKey(userId);
    assertEquals(1, result);
  }
}
