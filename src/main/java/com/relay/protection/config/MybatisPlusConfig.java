package com.relay.protection.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.relay.protection.service.common.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

/**
 * Mybatis plus 配置类。
 *
 * @author zhuHx
 */
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class MybatisPlusConfig {

    private static String CREATE_TIME = "createTime";
    private static String UPDATE_TIME = "updateTime";
    private static String CREATE_USER = "createUser";
    private static String UPDATE_USER = "updateUser";

    /**
     * 启动乐观锁
     * 支持mysql物理分页
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁拦截器
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //支持mysql
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 支持数据库创建时间和更新时间自动生成
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
                LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                this.setFieldValByName(CREATE_TIME, LocalDateTime.now(), metaObject);
                this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
                this.setFieldValByName(CREATE_USER, principal.getUser().getId(), metaObject);
                this.setFieldValByName(UPDATE_USER, principal.getUser().getId(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
                this.setFieldValByName(UPDATE_USER, principal.getUser().getId(), metaObject);
            }
        };
    }


}
