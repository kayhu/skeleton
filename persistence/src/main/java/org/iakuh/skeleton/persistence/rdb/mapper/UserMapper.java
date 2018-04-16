package org.iakuh.skeleton.persistence.rdb.mapper;

import org.apache.ibatis.annotations.Param;
import org.iakuh.skeleton.persistence.rdb.entity.User;
import org.iakuh.skeleton.persistence.rdb.example.UserExample;

import java.util.List;

public interface UserMapper extends GenericMapper<User, Long> {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
}