package org.iakuh.skeleton.api.service;

import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.common.model.vo.UserVo;

public interface UserService {

  UserVo getUserById(Long id) throws NotFoundException;

  UserVo addUser(UserVo user);
}
