package org.iakuh.skeleton.api.test.error;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.iakuh.skeleton.api.config.RootConfig;
import org.iakuh.skeleton.api.config.ServletConfig;
import org.iakuh.skeleton.api.controller.ExampleUserController;
import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@WebAppConfiguration
public class ExceptionHandlerMappingTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private ExampleUserController exampleUserController;

  @Autowired
  private HandlerExceptionResolver handlerExceptionResolver;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(exampleUserController)
        .setHandlerExceptionResolvers(handlerExceptionResolver).build();
  }

  @Test
  public void testUserNotFound() throws Exception {
    when(userService.getUserById(any())).thenThrow(new NotFoundException("User not found"));

    mockMvc.perform(get("/users/25"))
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_RESOURCE_NOT_FOUND\",\n"
            + "    \"message\": \"User not found\",\n"
            + "    \"details\": null\n"
            + "}"));
  }
}
