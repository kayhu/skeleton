package org.iakuh.skeleton.api.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.iakuh.skeleton.api.controller.ExampleUserController;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ExampleUserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private ExampleUserController exampleUserController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(exampleUserController).build();
  }

  @Test
  public void testGetUser() throws Exception {
    final UserVo userVo = new UserVo();
    userVo.setId(25L);
    userVo.setUsername("huk");
    userVo.setEnabled(true);

    when(userService.getUserById(any())).thenReturn(userVo);

    mockMvc.perform(get("/users/25"))
        .andExpect(status().isOk())
        .andExpect(content().json(userVo.toString()));
  }

  @Test
  public void testAddUser() throws Exception {
    final UserVo userVo = new UserVo();
    userVo.setId(25L);
    userVo.setUsername("huk");
    userVo.setEnabled(true);

    when(userService.addUser(any())).thenReturn(userVo);

    mockMvc.perform(post("/users")
        .contentType(APPLICATION_JSON_VALUE)
        .content(userVo.toString()))
        .andExpect(status().isOk())
        .andExpect(content().json(userVo.toString()));
  }

  @Test
  public void testUploadIdCard() throws Exception {
    mockMvc.perform(multipart("/users/25/idCard")
        .file("file", "test test test".getBytes()))
        .andExpect(status().isOk())
        .andExpect(content().json("{}"));
  }
}
