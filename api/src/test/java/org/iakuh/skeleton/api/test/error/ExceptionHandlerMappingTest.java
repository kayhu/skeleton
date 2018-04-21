package org.iakuh.skeleton.api.test.error;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.iakuh.skeleton.api.config.RootConfig;
import org.iakuh.skeleton.api.config.ServletConfig;
import org.iakuh.skeleton.api.controller.ExampleUserController;
import org.iakuh.skeleton.api.exception.NotFoundException;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
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
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(exampleUserController)
        .setHandlerExceptionResolvers(handlerExceptionResolver)
        .setValidator(new Validator() {
          @Override
          public boolean supports(Class<?> clazz) {
            return true;
          }

          @Override
          public void validate(Object target, Errors errors) {
            FieldError fieldError = new FieldError("", "username", "should not be null");
            ((BindingResult) errors).addError(fieldError);
          }
        }).build();
  }

  @Test
  public void testOnNotFoundException() throws Exception {
    when(userService.getUserById(any()))
        .thenThrow(new NotFoundException("error.user.not.found"));

    mockMvc.perform(get("/users/25"))
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_RESOURCE_NOT_FOUND\",\n"
            + "    \"message\": \"User not found\",\n"
            + "    \"details\": null\n"
            + "}"));
  }

  @Test
  public void testOnValidationException() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(APPLICATION_JSON_VALUE)
        .content(Collections.emptyMap().toString()))
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_VALIDATION_FAILURE\",\n"
            + "    \"message\": \"Object not valid\",\n"
            + "    \"details\": \"username should not be null\"\n"
            + "}"));
  }

  @Test
  public void testOnRuntimeException() throws Exception {
    when(userService.getUserById(any()))
        .thenThrow(new RuntimeException("Runtime exception error message."));

    mockMvc.perform(get("/users/25"))
        .andExpect(status().isInternalServerError())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_INTERNAL_SERVER_ERROR\",\n"
            + "    \"message\": \"System unknown exception\",\n"
            + "    \"details\": null\n"
            + "}"));
  }

  @Test
  public void testNoSuchMessageException() throws Exception {
    when(userService.getUserById(any()))
        .thenThrow(new NotFoundException("NoSuchMessageException"));

    mockMvc.perform(get("/users/25"))
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_RESOURCE_NOT_FOUND\",\n"
            + "    \"message\": \"NoSuchMessageException\",\n"
            + "    \"details\": null\n"
            + "}"));
  }
}
