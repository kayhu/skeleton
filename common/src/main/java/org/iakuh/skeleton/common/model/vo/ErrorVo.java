package org.iakuh.skeleton.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.iakuh.skeleton.common.model.BaseModel;

@ApiModel("错误信息")
@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class ErrorVo extends BaseModel {
    @ApiModelProperty("错误代码")
    private String code;
    @ApiModelProperty("错误描述")
    private String message;
    @ApiModelProperty("错误详情")
    private String details;

    private static final String SEPARATOR = ";";

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
