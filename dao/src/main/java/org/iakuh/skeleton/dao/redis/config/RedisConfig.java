package org.iakuh.skeleton.dao.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan("org.iakuh.skeleton.dao.redis")
@PropertySource("classpath:dao-config.properties")
@EnableRedisRepositories("org.iakuh.skeleton.dao.redis.repository")
public class RedisConfig {

  @Value("${dao.redis.hostname}")
  private String hostname;

  @Value("${dao.redis.port}")
  private int port;

  @Value("${dao.redis.database}")
  private int database;

  @Value("${dao.redis.password}")
  private String password;

  @Bean
  public RedisPassword redisPassword() {
    return RedisPassword.of(password);
  }

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(hostname);
    configuration.setPort(port);
    configuration.setDatabase(database);
    configuration.setPassword(redisPassword());
    return configuration;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory(
      RedisStandaloneConfiguration redisStandaloneConfiguration) {
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    return new StringRedisTemplate(redisConnectionFactory);
  }

  @Bean
  RedisTemplate jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<?, ?> redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }
}
