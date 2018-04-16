package org.iakuh.skeleton.common;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Void.class)
@ActiveProfiles("dev")
public class BaseTest extends TestCase {
    @Test
    public void dummyTest() {
    }
}
