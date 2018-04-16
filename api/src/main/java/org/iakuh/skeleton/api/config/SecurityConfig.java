package org.iakuh.skeleton.api.config;

import org.iakuh.skeleton.security.entry.BasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:config/api.properties")
@EnableWebSecurity
@Import(ServletConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint(HandlerExceptionResolver handlerExceptionResolver) {
        BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
        authenticationEntryPoint.setHandlerExceptionResolver(handlerExceptionResolver);
        authenticationEntryPoint.setRealmName(env.getProperty("realm.name"));
        return authenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/documentation/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/health").permitAll()
                .anyRequest().authenticated()
//                .and().formLogin()
//                .and().logout()
                .and().httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint(handlerExceptionResolver))
                // throw AccessDeniedException which will be handled in ExceptionController
                .and().exceptionHandling().accessDeniedHandler((req, resp, ex) -> {throw ex;})
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}
