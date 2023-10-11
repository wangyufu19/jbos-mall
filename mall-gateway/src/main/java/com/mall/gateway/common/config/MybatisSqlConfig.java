package com.mall.gateway.common.config;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MybatisSqlConfig
 *
 * @author youfu.wang
 * @date 2023/10/9 9:46
 */
@Component
@Slf4j
public class MybatisSqlConfig implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * enableSqlTrace
     */
    @Value("${mybatis-plus.isPrintSql:false}")
    private boolean isPrintSql;
    /**
     * sqlSessionFactoryList
     */
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    private boolean containsInterceptor(org.apache.ibatis.session.Configuration configuration, Interceptor interceptor) {
        try {
            return configuration.getInterceptors().contains(interceptor);
        } catch (Exception var4) {
            return false;
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        MyBatisSqlInterceptor myBatisSqlInterceptor = new MyBatisSqlInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!this.containsInterceptor(configuration, myBatisSqlInterceptor)) {
                configuration.addInterceptor(myBatisSqlInterceptor);
            }
        }
    }

    @Intercepts({@Signature(
            type = Executor.class,
            method = "update",
            args = {MappedStatement.class, Object.class}
    ), @Signature(
            type = Executor.class,
            method = "query",
            args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
    ), @Signature(
            type = Executor.class,
            method = "query",
            args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
    )})
    public class MyBatisSqlInterceptor implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            BoundSql boundSql = ms.getBoundSql(parameter);
            if (isPrintSql) {
                log.info("=============sql={}", boundSql.getSql());
            }

            Object result = invocation.proceed();
            long endTime = System.currentTimeMillis();
            //sql执行耗时
            long sqlCost = endTime - startTime;
            log.info("============={} 执行耗时{}毫秒", ms.getId(), sqlCost);
            return result;
        }
    }
}
