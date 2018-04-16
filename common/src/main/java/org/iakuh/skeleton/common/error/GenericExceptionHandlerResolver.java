package org.iakuh.skeleton.common.error;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

public class GenericExceptionHandlerResolver extends ExceptionHandlerExceptionResolver {

  private ExceptionHandlerMapping exceptionHandlerMapping;

  private ExceptionHandlerMethodResolver exceptionHandlerMethodResolver;

  public GenericExceptionHandlerResolver() {
    setOrder(
        HIGHEST_PRECEDENCE); // to handle the error before Spring's DefaultHandlerExceptionResolver
  }

  public void setExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
    this.exceptionHandlerMapping = exceptionHandlerMapping;
  }

  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (exceptionHandlerMapping == null) {
      exceptionHandlerMapping = new ExceptionHandlerMapping();
    }
    exceptionHandlerMethodResolver = new ExceptionHandlerMethodResolver(
        exceptionHandlerMapping.getClass());
  }

  @Override
  protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
      Exception exception) {
    ServletInvocableHandlerMethod invocableHandlerMethod = super
        .getExceptionHandlerMethod(handlerMethod, exception);
    if (invocableHandlerMethod == null) {
      Method method = exceptionHandlerMethodResolver.resolveMethod(exception);
      if (method != null) {
        invocableHandlerMethod = new ServletInvocableHandlerMethod(exceptionHandlerMapping, method);
      }
    }
    return invocableHandlerMethod;
  }

  @Override
  protected ModelAndView doResolveHandlerMethodException(
      HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod,
      Exception exception) {
    if (handlerMethod != null) {
      request.setAttribute(ExceptionHandlerMapping.HANDLER_METHOD,
          handlerMethod.getBeanType().getSimpleName());
    }
    return super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
  }
}
