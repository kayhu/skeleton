package org.iakuh.skeleton.common.test.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.iakuh.skeleton.common.util.ApplicationContextUtil;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ContextConfiguration(classes = ApplicationContextUtilTest.class)
public class ApplicationContextUtilTest extends BaseTest {

  private static final String beanName = "testBean";

  @Autowired
  private ApplicationContextUtil appCtxUtil;

  @Bean
  public ApplicationContextUtil appCtxUtil() {
    return new ApplicationContextUtil();
  }

  @Bean(beanName)
  public TestBean testBean() {
    return new TestBean(beanName);
  }

  @Test
  public void getBeanByName() {
    TestBean bean = (TestBean) appCtxUtil.getBean(beanName);
    assertEquals(beanName, bean.getName());
  }

  @Test
  public void getBeanByType() {
    TestBean bean = appCtxUtil.getBean(TestBean.class);
    assertEquals(beanName, bean.getName());
  }

  @Test
  public void getBeanByNameAndType() {
    TestBean bean = appCtxUtil.getBean(beanName, TestBean.class);
    assertEquals(beanName, bean.getName());
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class TestBean {
    private String name;
  }
}
