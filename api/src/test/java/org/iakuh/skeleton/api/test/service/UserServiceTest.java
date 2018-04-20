package org.iakuh.skeleton.api.test.service;

import static org.mockito.Mockito.when;

import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.api.service.impl.UserServiceImpl;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.repository.UserRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest extends BaseTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

  }

  @Test
  public void testGetUserById() throws NotFoundException {
    final Long id = 1L;
    final String username = "huk";
    final Boolean enabled = false;
    final User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setEnabled(enabled);

    when(userRepository.selectByPrimaryKey(1L)).thenReturn(user);
    UserVo result = userService.getUserById(1L);
    assertEquals(id, result.getId());
    assertEquals(username, result.getUsername());
    assertEquals(enabled, result.getEnabled());
  }

  @Test(expected = NotFoundException.class)
  public void testGetUserByIdNotFound() throws NotFoundException {
    when(userRepository.selectByPrimaryKey(1L)).thenReturn(null);
    userService.getUserById(1L);
  }
}
