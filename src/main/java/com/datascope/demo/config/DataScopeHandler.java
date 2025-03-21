package com.datascope.demo.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.datascope.demo.aspect.DataScopeAspect;
import com.datascope.demo.aspect.DataScopeParam;
import com.datascope.demo.enums.DataScopeEnum;
import com.datascope.demo.aspect.sqlapply.PermissionSelector;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maj
 * @version 1.0
 * @description: 数据权限mybatis自定义Handler
 * @date 2023/11/27 23:25
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {


    @Autowired
    private PermissionSelector permissionSelector;

    @SneakyThrows
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        if (where == null) {
            where = new HexValue(" 1 = 1 ");
        }
        DataScopeParam dataScopeParam = DataScopeAspect.getDataScopeParam();
        if (ObjectUtil.isEmpty(dataScopeParam)) {
            return where;
        }
        List<DataScopeEnum> scopeList = dataScopeParam.getScopeList();
        List<Expression> newWhereList = new ArrayList<>();
        //获取当前需要增加哪些数据权限
        for (DataScopeEnum scopeEnum : scopeList) {
            Expression newWhere = permissionSelector.getStrategyMap().get(scopeEnum.getCode()).apply(dataScopeParam);
            log.info("当前权限为：{}，权限表达式为：{}", scopeEnum.getCode(), newWhere);
            if (ObjectUtil.isEmpty(newWhere)) {
                continue;
            }
            newWhereList.add(newWhere);
        }
        //封装需要返回的where条件
        if (CollectionUtil.isNotEmpty(newWhereList)) {
            Expression combinedConditions = newWhereList.stream().reduce((expr1, expr2) -> new AndExpression(expr1, new Parenthesis(expr2))).orElse(null);
            return where == null ? combinedConditions : new AndExpression(where, new Parenthesis(combinedConditions));
        }
        return where;
    }

}
