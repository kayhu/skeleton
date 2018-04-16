package org.iakuh.skeleton.common.config;

import java.util.HashMap;
import java.util.Map;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/common.properties")
public class RedisCacheConfig {

  public static final String CACHE_TTL_10S = "cache_ttl_10s";
  public static final String CACHE_TTL_30M = "cache_ttl_30m";

  @Bean
  @Profile("dev")
  public Config singleServerConfig(@Value("${redis.cache.single.address}") String address,
      @Value("${redis.cache.single.database}") int database,
      @Value("${redis.cache.single.password}") String password) {
    Config config = new Config();
    config.useSingleServer()
        .setAddress(address)
        .setDatabase(database)
        .setPassword(password);
    return config;
  }

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient(Config config) {
    return Redisson.create(config);
  }

  @Bean
  public CacheManager cacheManager(RedissonClient redissonClient) {
    Map<String, CacheConfig> config = new HashMap<>();
    config.put(CACHE_TTL_10S, new CacheConfig(10 * 1000, 0));
    config.put(CACHE_TTL_30M, new CacheConfig(30 * 60 * 1000, 0));
    return new RedissonSpringCacheManager(redissonClient, config);
  }
}
