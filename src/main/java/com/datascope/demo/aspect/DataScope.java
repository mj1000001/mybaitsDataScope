package com.datascope.demo.aspect;

import com.datascope.demo.enums.DataScopeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 数据权限自定义注解
 */ public @interface DataScope {

    /**
     * 表别名
     *
     * @return
     */
    String[] alias() default "";

    /**
     * 自定义列名
     *
     * @return
     */
    String[] columns() default "";

    DataScopeEnum[] scope();
    /**
     * 作用的系统类型
     * @return
     */
    String scopePlateId() default "";





}
