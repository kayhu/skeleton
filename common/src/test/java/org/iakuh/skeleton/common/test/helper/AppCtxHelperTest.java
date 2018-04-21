package org.iakuh.skeleton.common.test.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.iakuh.skeleton.common.helper.AppCtxHelper;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ContextConfiguration(classes = AppCtxHelperTest.class)
public class AppCtxHelperTest extends BaseTest {

  private static final String beanName = "testBean";

  @Autowired
  private AppCtxHelper appCtxHelper;

  @Bean
  public AppCtxHelper appCtxUtil() {
    return new AppCtxHelper();
  }

  @Bean(beanName)
  public TestBean testBean() {
    return new TestBean(beanName);
  }

  @Bean("messageSource")
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("message");
    return messageSource;
  }

  @Test
  public void testGetBeanByName() {
    TestBean bean = (TestBean) appCtxHelper.getBean(beanName);
    assertEquals(beanName, bean.getName());
  }

  @Test
  public void testGetBeanByType() {
    TestBean bean = appCtxHelper.getBean(TestBean.class);
    assertEquals(beanName, bean.getName());
  }

  @Test
  public void testGetBeanByNameAndType() {
    TestBean bean = appCtxHelper.getBean(beanName, TestBean.class);
    assertEquals(beanName, bean.getName());
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class TestBean {

    private String name;
  }
}
