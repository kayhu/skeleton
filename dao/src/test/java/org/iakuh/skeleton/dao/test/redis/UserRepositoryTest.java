package org.iakuh.skeleton.dao.test.redis;

import java.util.Arrays;
import org.iakuh.skeleton.dao.redis.config.RedisConfig;
import org.iakuh.skeleton.dao.redis.entity.User;
import org.iakuh.skeleton.dao.redis.repository.UserRedisRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = RedisConfig.class)
public class UserRepositoryTest extends BaseTest {

  @Autowired
  private UserRedisRepository userRedisRepository;

  @Test
  public void testRedisRepository() {
    User user = new User();
    user.setId(1L);
    user.setUsername("kay_hu");
    user.setEmail("kay_hu@163.com");
    user.setTag(Arrays.asList("tall", "rich", "handsome"));
    userRedisRepository.save(user);
    User result = userRedisRepository.findById(1L).get();
    assertEquals(1, result.getId().longValue());
    assertEquals("kay_hu", result.getUsername());
    assertEquals("kay_hu@163.com", result.getEmail());
    assertTrue(result.getTag().size() == 3);
    User findByUsername = userRedisRepository.findByUsername("kay_hu");
    User findByEmail = userRedisRepository.findByEmail("kay_hu@163.com");
  }

}
