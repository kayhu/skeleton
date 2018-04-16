package org.iakuh.skeleton.dao.redis.entity;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.iakuh.skeleton.dao.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@RedisHash
public class User extends BaseEntity {

  @Id
  private Long id;
  @Indexed
  private String username;
  @Indexed
  private String email;
  private List<String> tag;
}