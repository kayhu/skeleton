package org.iakuh.skeleton.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.iakuh.skeleton.common.model.BaseModel;

@ApiModel("错误信息")
@Getter
@Setter
@AllArgsConstructor
public class ErrorVo extends BaseModel {

  private static final String SEPARATOR = ";";
  @ApiModelProperty("错误代码")
  private String code;
  @ApiModelProperty("错误描述")
  private String message;
  @ApiModelProperty("错误详情")
  private String details;

  public void addDetail(String detail) {
    if (!StringUtils.isBlank(details)) {
      details = details + SEPARATOR + detail;
    } else {
      details = detail;
    }
  }

  public void clearDetails() {
    details = "";
  }
}
