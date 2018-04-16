package org.iakuh.skeleton.dao.rdb.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.example.UserExample;

public interface UserMapper extends GenericMapper<User, Long> {

  long countByExample(UserExample example);

  int deleteByExample(UserExample example);

  List<User> selectByExample(UserExample example);

  int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

  int updateByExample(@Param("record") User record, @Param("example") UserExample example);
}