package cn.canghe.robot.groupsave;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author canghe
 */
@SpringBootApplication
@ComponentScan({"cn.canghe"})
@EnableScheduling
@EnableCaching
@EnableAsync
@MapperScan("cn.canghe.robot.groupsave.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class GroupSaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupSaveApplication.class, args);
    }

    @Bean
    public Executor serviceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        //设置核心线程数
        executor.setCorePoolSize(core);
        //设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        //除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(3);
        //如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(50);
        //线程名称前缀
        executor.setThreadNamePrefix("service-execute");
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
