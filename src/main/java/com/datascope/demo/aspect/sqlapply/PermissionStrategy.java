package com.datascope.demo.aspect.sqlapply;

import cn.hutool.core.util.ObjectUtil;
import com.datascope.demo.enums.DataScopeEnum;
import com.datascope.demo.aspect.DataScopeParam;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author maj
 * @version 1.0
 * @description: 数据权限封装接口
 * @date 2023/11/28 1:40
 */
public interface PermissionStrategy {


    /**
     * 封装对应的字段和数据
     *
     * @param dataScopeParam
     * @return
     */
    Expression apply(DataScopeParam dataScopeParam);


    String getPermissionCode();

    default Column getColumn(DataScopeEnum scopeEnum, DataScopeParam dataScopeParam) {

        if (scopeEnum == null || dataScopeParam == null) {
            throw new IllegalArgumentException("ScopeEnum and DataScopeParam must not be null.");
        }

        String defaultColumn = scopeEnum.getColumn();
        String[] columns = Optional.ofNullable(dataScopeParam.getColumns()).orElse(new String[0]);
        String[] deAlias = Optional.ofNullable(dataScopeParam.getAlias()).orElse(new String[0]);
        // 获取匹配的列
        String columnAlias = IntStream.range(0, Math.min(columns.length, dataScopeParam.getScopeList().size()))
                .filter(i -> scopeEnum.getCode().equals(dataScopeParam.getScopeList().get(i).getCode()))
                .mapToObj(i -> columns[i]).findFirst().orElse(defaultColumn);
        String alias = ObjectUtil.isEmpty(deAlias) ? "" : IntStream.range(0, Math.min(columns.length, dataScopeParam.getScopeList().size()))
                .filter(i -> scopeEnum.getCode().equals(dataScopeParam.getScopeList().get(i).getCode()))
                .mapToObj(i -> (i <= (deAlias.length-1) && ObjectUtil.isNotEmpty(deAlias[i])) ? deAlias[i] : "").findFirst().orElse("");
        // 拼接sql

        String sql = StringUtils.isEmpty(alias) ? columnAlias : String.format("%s.%s", alias, columnAlias);
        return new Column(sql);

    }

    /*static void main(String[] args) {
        DataScopeEnum scopeEnum = DataScopeEnum.CITY;

        DataScopeParam dataScopeParam = DataScopeParam.builder().alias(new String[]{"c"}).scopeList(Arrays.asList(DataScopeEnum.BUSINESS, DataScopeEnum.CITY)).columns(new String[]{"bus_", "city_ "}).build();

        if (scopeEnum == null || dataScopeParam == null) {
            throw new IllegalArgumentException("ScopeEnum and DataScopeParam must not be null.");
        }

        String defaultColumn = scopeEnum.getColumn();
        String[] columns = Optional.ofNullable(dataScopeParam.getColumns()).orElse(new String[0]);
        String[] deAlias = Optional.ofNullable(dataScopeParam.getAlias()).orElse(new String[0]);
        // 获取匹配的列
        String columnAlias = IntStream.range(0, Math.min(columns.length, dataScopeParam.getScopeList().size()))
                .filter(i -> scopeEnum.getCode().equals(dataScopeParam.getScopeList().get(i).getCode()))
                .mapToObj(i -> columns[i]).findFirst().orElse(defaultColumn);
        String alias = ObjectUtil.isEmpty(deAlias) ? "" : IntStream.range(0, Math.min(columns.length, dataScopeParam.getScopeList().size()))
                .filter(i -> scopeEnum.getCode().equals(dataScopeParam.getScopeList().get(i).getCode()))
                .mapToObj(i -> (i <= (deAlias.length-1) && ObjectUtil.isNotEmpty(deAlias[i])) ? deAlias[i] : "").findFirst().orElse("");
        // 拼接sql
        String sql = StringUtils.isEmpty(alias) ? columnAlias : String.format("%s.%s", alias, columnAlias);
        System.out.println(sql);


    }*/

}
