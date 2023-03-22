/**
 *
 */
package cn.canghe.robot.groupsave.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author cang he
 *
 */
@Slf4j
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数据
        executor.setMaxPoolSize(20);
        // 缓存队列
        executor.setQueueCapacity(400);
        // 空闲时间
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("cardTaskExecutor-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 关闭时等待任务执行完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 关闭等待时间
        executor.setAwaitTerminationSeconds(60);

        log.info("---------------------->配置线程池：taskExecutor");
        return executor;
    }

    /**
     * 会话留存处理
     */
    @Bean
    public ThreadPoolTaskExecutor sessionArchiveExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(400);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("session-archive-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        log.info("---------------------->配置线程池：sessionArchiveExecutor");
        return executor;
    }
}
