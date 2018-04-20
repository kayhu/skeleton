package org.iakuh.skeleton.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  public Object getBean(String name) {
    return applicationContext.getBean(name);
  }

  public <T> T getBean(String name, Class<T> requiredType) {
    return applicationContext.getBean(name, requiredType);
  }

  public <T> T getBean(Class<T> requiredType) {
    return applicationContext.getBean(requiredType);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
}