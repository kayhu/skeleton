package org.iakuh.skeleton.dao.rdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.iakuh.skeleton.dao.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity {

  private Long id;
  private String username;
  private String password;
  private Boolean enabled;
}