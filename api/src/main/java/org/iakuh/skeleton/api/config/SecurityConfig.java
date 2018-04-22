package org.iakuh.skeleton.api.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import javax.sql.DataSource;
import org.iakuh.skeleton.security.entry.BasicAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@PropertySource("classpath:api-config.properties")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${realm.name}")
  private String realmName;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private HandlerExceptionResolver handlerExceptionResolver;

  @Bean
  public BasicAuthEntryPoint basicAuthEntryPoint(
      HandlerExceptionResolver handlerExceptionResolver) {
    BasicAuthEntryPoint basicAuthEntryPoint = new BasicAuthEntryPoint();
    basicAuthEntryPoint.setHandlerExceptionResolver(handlerExceptionResolver);
    basicAuthEntryPoint.setRealmName(realmName);
    return basicAuthEntryPoint;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/statistics/**").permitAll()
        .antMatchers("/documentation/**").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/v2/api-docs").permitAll()
        .antMatchers("/health").permitAll()
        .anyRequest().authenticated()
        /*.and()
        .formLogin()
        .and()
        .logout()*/
        .and()
        .httpBasic()
        .authenticationEntryPoint(basicAuthEntryPoint(handlerExceptionResolver))
        // throw AccessDeniedException which will be handled in ExceptionController
        .and()
        .exceptionHandling()
        .accessDeniedHandler((req, resp, ex) -> {
          throw ex;
        })
        .and()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .csrf()
        .disable();
  }
}