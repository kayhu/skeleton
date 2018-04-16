package org.iakuh.skeleton.api.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static Object getBean(String name) {
    return ApplicationContextUtil.applicationContext.getBean(name);
  }

  public static Object getBean(String name, Object... args) {
    return ApplicationContextUtil.applicationContext.getBean(name, args);
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return ApplicationContextUtil.applicationContext.getBean(name, requiredType);
  }

  public static <T> T getBean(Class<T> requiredType) {
    return ApplicationContextUtil.applicationContext.getBean(requiredType);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextUtil.applicationContext = applicationContext;
  }
}
