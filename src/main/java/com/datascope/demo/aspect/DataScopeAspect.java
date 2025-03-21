package com.datascope.demo.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.datascope.demo.enums.DataScopeEnum;
import com.datascope.demo.vo.UserPowerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author maj
 * @version 1.0
 * @description: 自定义注解的实现
 * @date 2023/11/28 1:40
 */

@Aspect
@Slf4j
@Component
public class DataScopeAspect {


    private static final String NINE = "9999999999999999999999";

    private static ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();


    public static DataScopeParam getDataScopeParam() {
        return threadLocal.get();
    }

    @Pointcut("@annotation(com.datascope.demo.aspect.DataScope)")
    public void methodPointCut() {
    }

    @After("methodPointCut()")
    public void clearThreadLocal() {
        threadLocal.remove();
    }

    @Before("methodPointCut()")
    public void doBefore(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method == null) {
            return;
        }
        // 获得注解
        DataScope dataScope = method.getAnnotation(DataScope.class);
        try {
            UserPowerVo userPowerVo = getCurrentUserInfo();
            if (dataScope != null && ObjectUtil.isNotEmpty(userPowerVo) && StringUtils.isNotEmpty(userPowerVo.getAccountKey())) {
                List<DataScopeEnum> scopeList = new ArrayList<>();
                String accountKey = userPowerVo.getAccountKey();
                if (dataScope.scope().length > 0) {
                    for (DataScopeEnum scopeEnum : dataScope.scope()) {
                        scopeList.add(scopeEnum);
                    }
                }
                if (CollectionUtil.isEmpty(scopeList)) {
                    return;
                }
                String plateId = userPowerVo.getPlateId();
                //如果指定了系统，并且系统id不等于当前登录人的系统id则数据权限不生效
                if (StringUtils.isNotEmpty(dataScope.scopePlateId()) && !dataScope.scopePlateId().equals(plateId)) {
                    return;
                }
                List<String> cityList = new ArrayList<>();
                Set<String> businessList = new HashSet<>();
                List<String> regionList = new ArrayList<>();
                cityList.addAll(getUserCityCodeScope(userPowerVo));
                log.info("dataScope  cityList============{}", cityList);
                businessList.addAll(getUserBusinessScope(userPowerVo));
                log.info("dataScope  businessList============{}", businessList);
                regionList.addAll(getUserRegionScope(userPowerVo));
                log.info("dataScope  regionList============{}", regionList);
                // 拿到注解的值
                String[] alias = dataScope.alias();
                String[] columns = dataScope.columns();
                DataScopeParam dataScopeParam = DataScopeParam.builder().alias(alias).accountKey(accountKey).columns(columns).scopeList(scopeList).cityList(cityList).businessList(new ArrayList<>(businessList)).regionList(regionList).build();
                log.info("===dataScopeParam=== {}", JSON.toJSONString(dataScopeParam));
                threadLocal.set(dataScopeParam);
            }
        } catch (Exception e) {
            log.error("数据权限 method 切面错误：", e);
            throw new RuntimeException("数据权限 method 切面错误：" + e);
        }

    }

    /**
     * 获取当前用户登录信息
     *
     * @return
     */
    private UserPowerVo getCurrentUserInfo() {
        // 从redis中获取用户信息
        return new UserPowerVo();
    }

    /**
     * 获取用户区域权限
     * @param userPowerVo
     * @return
     */
    private List<String> getUserRegionScope(UserPowerVo userPowerVo) {

        List<String> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userPowerVo.getRegionList())) {
            userPowerVo.getRegionList().forEach(i -> {
                list.add(i.getCode());
            });
            return list;
        }
        return list;
    }

    /**
     * 获取用户城市权限
     * @param userPowerVo
     * @return
     */
    private List<String> getUserCityCodeScope(UserPowerVo userPowerVo) {

        List<String> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userPowerVo.getCityList())) {
            userPowerVo.getCityList().forEach(i -> {
                list.add(i.getCode());
            });

        }
        if (CollectionUtil.isNotEmpty(userPowerVo.getProvinceList())) {
            userPowerVo.getProvinceList().forEach(i -> {
                list.add(i.getCode());
            });
        }
        if (CollectionUtil.isNotEmpty(userPowerVo.getRegionList())) {
            userPowerVo.getRegionList().forEach(i -> {
                list.add(i.getCode());
            });
        }
        return list;
    }

    /**
     * 获取用户业务线权限
     * @param userPowerVo
     * @return
     */
    private Set<String> getUserBusinessScope(UserPowerVo userPowerVo) {

        Set<String> list = new HashSet<>();
        if (CollectionUtil.isNotEmpty(userPowerVo.getBusinessList())) {
            List<String> businessList = userPowerVo.getBusinessList();
            return new HashSet<>(businessList);
        }
        return list;
    }

}
