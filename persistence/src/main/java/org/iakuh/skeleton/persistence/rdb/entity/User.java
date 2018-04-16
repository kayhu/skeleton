package org.iakuh.skeleton.persistence.rdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.iakuh.skeleton.persistence.common.entity.BaseEntity;

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