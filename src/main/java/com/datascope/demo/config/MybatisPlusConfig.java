package com.datascope.demo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    @Resource
    private DataScopeHandler myDataPermissionHandler;



    // 最新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加数据权限插件
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        // 添加自定义的数据权限处理器
        dataPermissionInterceptor.setDataPermissionHandler(myDataPermissionHandler);
        interceptor.addInnerInterceptor(dataPermissionInterceptor);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
