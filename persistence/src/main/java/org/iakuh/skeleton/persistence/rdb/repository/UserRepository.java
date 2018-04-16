package org.iakuh.skeleton.persistence.rdb.repository;

import org.iakuh.skeleton.persistence.rdb.entity.User;
import org.iakuh.skeleton.persistence.rdb.mapper.GenericMapper;
import org.iakuh.skeleton.persistence.rdb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends GenericRepository<User, Long> {

    @Autowired
    private UserMapper mapper;

    @Override
    protected GenericMapper<User, Long> getGenericMapper() {
        return mapper;
    }
}
