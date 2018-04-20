package org.iakuh.skeleton.common.test.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.iakuh.skeleton.common.util.ServletContextUtil;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class ServletContextUtilTest extends BaseTest {

  @Test
  public void testGetRequest() {
    HttpServletRequest request = ServletContextUtil.getRequest();
    assertNotNull(request);
  }
  @Test
  public void testGetResponse() {
    HttpServletResponse response = ServletContextUtil.getResponse();
  }

  @Test
  public void testGetSession() {
    HttpSession session = ServletContextUtil.getSession();
    session.setAttribute("test", "pass");
    assertEquals("pass", session.getAttribute("test"));
  }

  @Test
  public void testGetSessionCreateFalse() {
    HttpSession session = ServletContextUtil.getSession(false);
    assertNull(session);
  }
}
