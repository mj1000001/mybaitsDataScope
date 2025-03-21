package com.datascope.demo.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "UserPowerVo", description = "用户拥有的权限信息")
public class UserPowerVo extends Model<UserPowerVo> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户账号")
    private  String accountKey;

    @ApiModelProperty(value = "省")
    private List<CodeVO> provinceList;

    @ApiModelProperty(value = "市")
    private List<CodeVO> cityList;

    @ApiModelProperty(value = "区域")
    private List<CodeVO> regionList;

    @ApiModelProperty(value = "业务线")
    private List<String> businessList;

    @ApiModelProperty(value = "管理员类型：customer_manager:客服主管")
    private List<String> manageType;

    @ApiModelProperty(value = "权限类型：business_auth:业务线权限，region_auth:区域权限，manager_auth:主管权限")
    private List<String> authType;

    @ApiModelProperty(value = "平台id")
    private String plateId;

}
