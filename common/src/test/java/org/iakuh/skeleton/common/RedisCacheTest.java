package org.iakuh.skeleton.common;

import static org.iakuh.skeleton.common.config.RedisCacheConfig.CACHE_TTL_10S;

import org.iakuh.skeleton.common.config.RedisCacheConfig;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = RedisCacheConfig.class)
public class RedisCacheTest extends BaseTest {

  @Autowired
  private CacheManager cacheManager;

  @Test
  public void testTtlAndMaxIdleTime() throws InterruptedException {
    final String key = "test test test";
    final String value = "pass! pass! pass!";
    Cache cache = cacheManager.getCache(CACHE_TTL_10S);
    cache.put(key, value);
    String getValue = cache.get(key, String.class);
    assertEquals(getValue, value);
    Thread.sleep(10000);
    getValue = cache.get(key, String.class);
    assertNull(getValue);
  }

  @After
  public void cleanup() {
    cacheManager.getCache(CACHE_TTL_10S).clear();
  }
}
