package com.datascope.demo.aspect.sqlapply.impl;

import cn.hutool.core.util.ObjectUtil;
import com.datascope.demo.enums.DataScopeEnum;
import com.datascope.demo.aspect.DataScopeParam;
import com.datascope.demo.aspect.sqlapply.PermissionStrategy;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maj
 * @version 1.0
 * @description: 店铺的数据权限封装
 * @date 2023/11/28 2:12
 */

@Service
@Slf4j
public class StorePermissionImpl implements PermissionStrategy {
    @Override
    public Expression apply(DataScopeParam dataScopeParam) {
        List<String> storeList = dataScopeParam.getStoreList();
        if(ObjectUtil.isEmpty(storeList)){
            return null;
        }
        Column column = getColumn(DataScopeEnum.STORE, dataScopeParam);
        ItemsList itemsList = new ExpressionList(storeList.stream().map(StringValue::new).collect(Collectors.toList()));
        InExpression inExpression = new InExpression(column, itemsList);
        return inExpression;
    }

    @Override
    public String getPermissionCode() {
        return DataScopeEnum.STORE.getCode();
    }
}
