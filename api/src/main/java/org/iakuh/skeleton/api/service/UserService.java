package org.iakuh.skeleton.api.service;

import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  UserVo getUserById(Long id) throws NotFoundException;

  UserVo addUser(UserVo user);
}
