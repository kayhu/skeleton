package org.iakuh.skeleton.dao.rdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iakuh.skeleton.dao.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

  private Long id;
  private String username;
  private String password;
  private Boolean enabled;
}