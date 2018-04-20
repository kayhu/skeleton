package org.iakuh.skeleton.common.test.model;

import org.iakuh.skeleton.common.model.vo.ErrorVo;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Test;

public class ErrorVoTest extends BaseTest {
  @Test
  public void testDetails() {
    ErrorVo vo = new ErrorVo(null, null, null);
    assertNull(vo.getDetails());
    vo.addDetail("1");
    assertEquals("1", vo.getDetails());
    vo.addDetail("2");
    assertEquals("1;2", vo.getDetails());
    vo.clearDetails();
    assertEquals("", vo.getDetails());
    vo.addDetail("1");
    assertEquals("1", vo.getDetails());
  }
}
