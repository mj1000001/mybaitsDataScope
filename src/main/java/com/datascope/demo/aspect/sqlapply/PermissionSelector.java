package com.datascope.demo.aspect.sqlapply;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author maj
 * @version 1.0
 * @description: TODO PermissionSelector描述
 * @date 2023/11/28 2:29
 */

@Component
@Slf4j
@Getter
public class PermissionSelector {

    private Map<String, PermissionStrategy> strategyMap;

    @Autowired
    public PermissionSelector(List<PermissionStrategy> strategys) {
        this.strategyMap = strategys.stream()
                .collect(Collectors.toMap(PermissionStrategy::getPermissionCode, Function.identity()));
    }
}
