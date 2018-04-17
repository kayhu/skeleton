package org.iakuh.skeleton.dao.rdb.config;

import com.github.pagehelper.PageInterceptor;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("org.iakuh.skeleton.dao.rdb")
@PropertySource("classpath:dao-config.properties")
@EnableTransactionManagement
public class MybatisConfig implements EnvironmentAware {

  private Environment env;

  @Override
  public void setEnvironment(Environment environment) {
    this.env = environment;
  }

  @Bean
  public Interceptor pageInterceptor() {
    Properties properties = new Properties();
    properties.setProperty("helperDialect", "mysql");
    properties.setProperty("reasonable", "true");
    properties.setProperty("supportMethodsArguments", "true");
    properties.setProperty("params", "count=countSql");
    properties.setProperty("autoRuntimeDialect", "true");
    PageInterceptor pageInterceptor = new PageInterceptor();
    pageInterceptor.setProperties(properties);
    return pageInterceptor;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("dao.jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("dao.jdbc.url"));
    dataSource.setUsername(env.getProperty("dao.jdbc.username"));
    dataSource.setPassword(env.getProperty("dao.jdbc.password"));
    return dataSource;
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean("sqlSessionFactoryBean")
  public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,
      Interceptor pageInterceptor) throws IOException {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setTypeAliasesPackage(env.getProperty("dao.mybatis.entity.package"));
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] mapperLocations = resolver.getResources(
        "classpath*:" + env.getProperty("dao.mybatis.xml.package") + "/**/*.xml");
    sqlSessionFactoryBean.setMapperLocations(mapperLocations);
    sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
    return sqlSessionFactoryBean;
  }

  @Bean
  public MapperScannerConfigurer MapperScannerConfigurer() {
    MapperScannerConfigurer msc = new MapperScannerConfigurer();
    msc.setBasePackage(env.getProperty("dao.mybatis.mapper.package"));
    msc.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
    return msc;
  }
}
