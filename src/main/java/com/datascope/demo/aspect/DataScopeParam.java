package com.datascope.demo.aspect;

import com.datascope.demo.enums.DataScopeEnum;
import lombok.*;

import java.util.List;

/**
 * @author maj
 * @version 1.0
 * @description: 数据权限需要的参数
 * @date 2023/11/28 1:26
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataScopeParam {

    /**
     * 表的别名
     */
    private String[] alias;

    /**
     * 指定字段名
     */
    private String[] columns;

    /**
     * 用户key
     */
    private String accountKey;

    /**
     * 作用范围
     */
    private List<DataScopeEnum> scopeList;
    /**
     * 用户的授权的城市
     */
    private List<String> cityList;
    /**
     * 用户授权的店铺
     */
    private List<String> storeList;

    /**
     * 用户授权的业务线
     */
    private List<String> businessList;
    /**
     * 用户授权的区域
     */
    private List<String> regionList;

}
