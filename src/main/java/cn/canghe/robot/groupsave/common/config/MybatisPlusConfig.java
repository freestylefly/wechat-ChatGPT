package cn.canghe.robot.groupsave.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cang he
 * @date 2019-07-01 5:37 PM
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptorMysql() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        page.setLimit(5000);
        return page;
    }
    /**
     * SQL执行效率插件 设置 local 环境开启
     * @return PerformanceInterceptor
     */
    @Bean
    // @Profile({"dev","test", "local"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        //interceptor.setFormat(true);
        interceptor.setMaxTime(60000);
        return interceptor;
    }
}
