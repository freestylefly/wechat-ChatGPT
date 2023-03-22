package cn.canghe.robot.groupsave.common.aop;

import java.lang.annotation.*;


/**
 * @author imyzt
 * 分布式定时任务唯一性保证锁
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 任务名称
     */
    String taskName();

    /**
     * 锁
     */
    String lockName();

    /**
     * 过期时间, 0等于没设置过期时间, 单位毫秒
     *
     * 定时任务情况特殊, 只能让第一个设备获得锁
     * 并且基本所有实例都会在同一个cron时间点开始竞争锁, 过期时间(大于1秒, 小于定时任务时间间隔), 基本就可以保证任务不被重复执行
     */
    int expireTime() default 1000 * 3;

    /**
     * 日志级别, 默认info
     * 1-info
     * 2-debug
     */
    LogLevel logLevel() default LogLevel.INFO;

    /**
     * 日志级别
     */
    enum LogLevel {
        /**
         * debug
         */
        DEBUG,
        /**
         * info
         */
        INFO
    }
}
