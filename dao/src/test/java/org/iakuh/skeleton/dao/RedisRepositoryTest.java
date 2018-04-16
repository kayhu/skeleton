package org.iakuh.skeleton.dao;

import java.util.Arrays;
import org.iakuh.skeleton.dao.redis.config.RedisConfig;
import org.iakuh.skeleton.dao.redis.entity.User;
import org.iakuh.skeleton.dao.redis.repository.UserRedisRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = RedisConfig.class)
public class RedisRepositoryTest extends BaseTest {

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
    User findById = userRedisRepository.findOne(1L);
    User findByUsername = userRedisRepository.findByUsername("kay_hu");
    User findByEmail = userRedisRepository.findByEmail("kay_hu@163.com");
  }

}
