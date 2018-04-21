package org.iakuh.skeleton.api.config;

import org.iakuh.skeleton.common.config.CommonConfig;
import org.iakuh.skeleton.common.helper.AppCtxHelper;
import org.iakuh.skeleton.dao.rdb.config.MybatisConfig;
import org.iakuh.skeleton.dao.redis.config.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(
    basePackages = "org.iakuh.skeleton.api",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
    })
@Import({
    CommonConfig.class,
    RedisConfig.class,
    MybatisConfig.class})
public class RootConfig {

  @Bean
  public AppCtxHelper appCtxUtil() {
    return new AppCtxHelper();
  }
}