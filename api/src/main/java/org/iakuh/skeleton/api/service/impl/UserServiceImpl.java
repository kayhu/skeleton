package org.iakuh.skeleton.api.service.impl;

import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.iakuh.skeleton.persistence.rdb.entity.User;
import org.iakuh.skeleton.persistence.rdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
