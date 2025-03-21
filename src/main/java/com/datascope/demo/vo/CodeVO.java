package com.datascope.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeVO implements Serializable {

    @ApiModelProperty("code")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("父级")
    private String parentCode;
}
