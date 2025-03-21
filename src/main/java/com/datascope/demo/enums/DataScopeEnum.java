package com.datascope.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限的作用范围
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {


    STORE("STORE", "STORE_KEY", "店铺"),

    BUSINESS("BUSINESS", "BUSINESS_TYPE", "业务线"),

    CITY("CITY", "CITY_CODE", "城市"),

    REGION("REGION", "REGION_CODE", "区域"),

    BUSINESS_CITY("BUSINESS_CITY", "BUSINESS_CITY", "城市-订单");

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限对应的数据库字段
     */
    private String column;

    /**
     * 描述
     */
    private String desc;


    public String getCode() {
        return this.code;
    }

    public String getColumn() {
        return this.column;
    }


}
