package org.iakuh.skeleton.api.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.iakuh.skeleton.api.controller.HealthController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HealthControllerTest {

  @InjectMocks
  private HealthController healthController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
  }

  @Test
  public void testHealth() throws Exception {
    mockMvc.perform(get("/health"))
        .andExpect(status().isOk())
        .andExpect(content().string("{}"));
  }

}
