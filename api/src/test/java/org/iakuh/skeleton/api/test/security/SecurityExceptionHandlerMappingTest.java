package org.iakuh.skeleton.api.test.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;
import org.iakuh.skeleton.api.config.RootConfig;
import org.iakuh.skeleton.api.config.ServletConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    WithSecurityContextTestExecutionListener.class})
@WebAppConfiguration
public class SecurityExceptionHandlerMappingTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private Filter springSecurityFilterChain;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .addFilters(springSecurityFilterChain).build();
  }

  @Test
  public void testUnauthorized() throws Exception {
    mockMvc.perform(get("/users/25"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_AUTHENTICATION_ERROR\",\n"
            + "    \"message\": \"Full authentication is required to access this resource\",\n"
            + "    \"details\": null\n"
            + "}"));
  }

  @Test
  public void testForbidden() throws Exception {
    mockMvc.perform(get("/users/25")
        .with(user("tester").password("password").roles("USER")))
        .andExpect(status().isForbidden())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_RESOURCE_ACCESS_DENIED\",\n"
            + "    \"message\": \"Access is denied\",\n"
            + "    \"details\": null\n"
            + "}"));
  }

  @Test
  public void testBadCredentials() throws Exception {
    mockMvc.perform(get("/users/25")
        .with(httpBasic("tester", "invalid")))
        .andExpect(status().isUnauthorized())
        .andExpect(content().json("{\n"
            + "    \"code\": \"E_AUTHENTICATION_ERROR\",\n"
            + "    \"message\": \"Bad credentials\",\n"
            + "    \"details\": null\n"
            + "}"));
  }
}
