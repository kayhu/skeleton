package org.iakuh.skeleton.api.config;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

import java.util.Collections;
import java.util.List;
import org.iakuh.skeleton.api.error.GlobalExceptionHandlerMapping;
import org.iakuh.skeleton.common.error.GenericExceptionHandlerResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Configuration
@ComponentScan(
    basePackages = "org.iakuh.skeleton.api.controller",
    includeFilters = {
        @ComponentScan.Filter(type = ANNOTATION, value = Controller.class),
        @ComponentScan.Filter(type = ANNOTATION, value = ControllerAdvice.class)
    })
public class ServletConfig extends WebMvcConfigurationSupport {

  private static final boolean jackson2Present =
      ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper",
          WebMvcConfigurationSupport.class.getClassLoader())
          && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator",
          WebMvcConfigurationSupport.class.getClassLoader());

  /**
   * Configure {@link ExceptionHandlerExceptionResolver} for adding default {@link
   * HandlerExceptionResolver}s. Same logic as {@link WebMvcConfigurationSupport}
   * addDefaultHandlerExceptionResolvers method <p>Adds the following exception resolvers: <ul>
   * <li>{@link GenericExceptionHandlerResolver} for handling exceptions through @{@link
   * ExceptionHandler} methods. <li>{@link ResponseStatusExceptionResolver} for exceptions annotated
   * with @{@link ResponseStatus}. <li>{@link DefaultHandlerExceptionResolver} for resolving known
   * Spring exception types </ul>
   */
  @Override
  protected void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    GenericExceptionHandlerResolver exHandlerResolver = new GenericExceptionHandlerResolver();
    exHandlerResolver.setExceptionHandlerMapping(new GlobalExceptionHandlerMapping());

    // Same logic as WebMvcConfigurationSupport.addDefaultHandlerExceptionResolvers() method
    exHandlerResolver.setContentNegotiationManager(mvcContentNegotiationManager());
    exHandlerResolver.setMessageConverters(getMessageConverters());
    exHandlerResolver.setCustomArgumentResolvers(getArgumentResolvers());
    exHandlerResolver.setCustomReturnValueHandlers(getReturnValueHandlers());
    if (jackson2Present) {
      exHandlerResolver.setResponseBodyAdvice(
          Collections.singletonList(new JsonViewResponseBodyAdvice()));
    }
    exHandlerResolver.setApplicationContext(getApplicationContext());
    exHandlerResolver.afterPropertiesSet();
    exceptionResolvers.add(exHandlerResolver);

    ResponseStatusExceptionResolver responseStatusResolver = new ResponseStatusExceptionResolver();
    responseStatusResolver.setMessageSource(getApplicationContext());
    exceptionResolvers.add(responseStatusResolver);

    exceptionResolvers.add(new DefaultHandlerExceptionResolver());
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController(
        "/documentation/v2/api-docs",
        "/v2/api-docs");

    registry.addRedirectViewController(
        "/documentation/swagger-resources/configuration/ui",
        "/swagger-resources/configuration/ui");

    registry.addRedirectViewController(
        "/documentation/swagger-resources/configuration/security",
        "/swagger-resources/configuration/security");

    registry.addRedirectViewController(
        "/documentation/swagger-resources",
        "/swagger-resources");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/documentation/swagger-ui.html**")
        .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

    registry.addResourceHandler("/documentation/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
