package org.iakuh.skeleton.common.test.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.iakuh.skeleton.common.helper.ServletCtxHelper;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class ServletCtxHelperTest extends BaseTest {

  @Test
  public void testGetRequest() {
    HttpServletRequest request = ServletCtxHelper.getRequest();
    assertNotNull(request);
  }
  @Test
  public void testGetResponse() {
    HttpServletResponse response = ServletCtxHelper.getResponse();
  }

  @Test
  public void testGetSession() {
    HttpSession session = ServletCtxHelper.getSession();
    session.setAttribute("test", "pass");
    assertEquals("pass", session.getAttribute("test"));
  }

  @Test
  public void testGetSessionCreateFalse() {
    HttpSession session = ServletCtxHelper.getSession(false);
    assertNull(session);
  }
}
