package org.iakuh.skeleton.api.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatchServletInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{RootConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{ServletConfig.class, SwaggerConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    /*servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class)
        .addMappingForUrlPatterns(null, false, "*//*");*/

    servletContext
        .addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8"))
        .addMappingForUrlPatterns(null, false, "/*");

    super.onStartup(servletContext);
  }

  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
        null, 2097152, 4194304, 0);
    registration.setMultipartConfig(multipartConfigElement);
  }
}
