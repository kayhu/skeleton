package org.iakuh.skeleton.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletContextUtil {

  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  public static HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  public static HttpSession getSession(boolean create) {
    return getRequest().getSession(create);
  }

  private static ServletRequestAttributes getRequestAttributes() {
    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
  }
}
