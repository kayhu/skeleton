package org.iakuh.skeleton.dao.common.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BaseEntity implements Serializable {

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
