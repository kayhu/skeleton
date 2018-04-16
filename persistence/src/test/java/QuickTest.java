import org.iakuh.skeleton.persistence.rdb.config.MybatisConfig;
import org.iakuh.skeleton.persistence.rdb.entity.User;
import org.iakuh.skeleton.persistence.rdb.repository.UserRepository;
import org.iakuh.skeleton.persistence.redis.config.RedisConfig;
import org.iakuh.skeleton.persistence.redis.repository.UserRedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MybatisConfig.class, RedisConfig.class})
@Transactional
@Rollback
public class QuickTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRedisRepository userRedisRepository;

    @Test
    public void testRdbRepository() {
        final String username = "iakuh";
        User user = new User();
        user.setUsername(username);
        user.setPassword("P@ssw0rd");
        user = userRepository.insertSelective(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testRedisRepository() {
        org.iakuh.skeleton.persistence.redis.entity.User user = new org.iakuh.skeleton.persistence.redis.entity.User();
        user.setId(1L);
        user.setUsername("kay_hu");
        user.setEmail("kay_hu@163.com");
        user.setTag(Arrays.asList("tall", "rich", "handsome"));
        userRedisRepository.save(user);
        org.iakuh.skeleton.persistence.redis.entity.User findById = userRedisRepository.findOne(1L);
        org.iakuh.skeleton.persistence.redis.entity.User findByUsername = userRedisRepository.findByUsername("kay_hu");
        org.iakuh.skeleton.persistence.redis.entity.User findByEmail = userRedisRepository.findByEmail("kay_hu@163.com");
    }

}
