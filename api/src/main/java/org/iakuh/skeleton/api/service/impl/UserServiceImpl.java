package org.iakuh.skeleton.api.service.impl;

import java.util.Objects;
import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserVo getUserById(Long id) throws NotFoundException {
    User user = userRepository.selectByPrimaryKey(id);

    if (Objects.isNull(user)) {
      throw new NotFoundException("User not found");
    }

    UserVo vo = new UserVo();
    vo.setId(user.getId());
    vo.setUsername(user.getUsername());
    vo.setEnabled(user.getEnabled());
    return vo;
  }

  @Override
  public UserVo addUser(UserVo vo) {
    return vo;
  }
}
