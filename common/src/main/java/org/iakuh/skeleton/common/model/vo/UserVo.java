package org.iakuh.skeleton.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.iakuh.skeleton.common.model.BaseModel;

@ApiModel("用户信息")
@Getter
@Setter
@NoArgsConstructor
public class UserVo extends BaseModel {

  @ApiModelProperty("用户编号")
  private Long id;
  @ApiModelProperty("用户名")
  @NotBlank
  private String username;
  @ApiModelProperty("是否启用")
  private Boolean enabled;
}
