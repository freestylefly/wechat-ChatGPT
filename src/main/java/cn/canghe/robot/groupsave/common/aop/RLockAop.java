package cn.canghe.robot.groupsave.common.aop;

import cn.canghe.robot.groupsave.common.exception.BusinessException;
import cn.canghe.robot.groupsave.common.response.ResultEnum;
import cn.canghe.robot.groupsave.common.util.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author imyzt
 */
@Aspect
@Component
@Slf4j
public class RLockAop {

    @Resource
    private RedisLockUtils redisLockUtils;

    @Pointcut("@annotation(cn.canghe.robot.groupsave.common.aop.DistributedLock)")
    public void distributedLockAspect() {

    }

    @Around("distributedLockAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String lockName = distributedLock.lockName();
        String taskName = distributedLock.taskName();
        String clientId = getHostName();

        if (StringUtils.isBlank(lockName)) {
            log.error("客户端[{}], 任务[{}], 没有指定锁对象", clientId, taskName);
            throw new BusinessException(ResultEnum.ERROR);
        }

        return redisLock(joinPoint, distributedLock, lockName, clientId);
    }

    private Object redisLock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock, String lockName, String clientId) throws Throwable {

        String lockValue = RandomStringUtils.random(4, "RandomStringUtils") + System.currentTimeMillis();

        try {
            if (!redisLockUtils.tryLock(lockName, lockValue, distributedLock.expireTime())) {
                return null;
            }

            printLogger(distributedLock, "获得锁, " + lockValue, clientId);
            return joinPoint.proceed();
        }  finally {

            if (redisLockUtils.releaseLuaLock(lockName, lockValue)) {
                printLogger(distributedLock, "释放锁, " + lockValue, clientId);
            } else {
                printLogger(distributedLock, "释放锁失败, 可能不是自己上的锁", clientId);
            }
        }
    }

    private String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    private void printLogger(DistributedLock distributedLock, String msg, String clientId) {

        String lockName = distributedLock.lockName();
        String taskName = distributedLock.taskName();
        DistributedLock.LogLevel logLevel = distributedLock.logLevel();

        // 客户端[A]{得到锁, 开始执行任务}[AAA], 锁对象
        if (DistributedLock.LogLevel.INFO == logLevel) {
            log.info("客户端[{}], 任务[{}], 锁对象 = {}, {}", clientId, taskName, lockName, msg);
        } else if (DistributedLock.LogLevel.DEBUG == logLevel) {
            log.debug("客户端[{}], 任务[{}], 锁对象 = {}, {}", clientId, taskName, lockName, msg);
        }
    }
}
