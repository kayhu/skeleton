package org.iakuh.skeleton.security.entry;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class BasicAuthenticationEntryPoint
    extends org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint {

  private HandlerExceptionResolver handlerExceptionResolver;

  public HandlerExceptionResolver getHandlerExceptionResolver() {
    return handlerExceptionResolver;
  }

  public void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    handlerExceptionResolver.resolveException(request, response, null, authException);
  }
}