package org.iakuh.skeleton.persistence.redis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.iakuh.skeleton.persistence.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

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