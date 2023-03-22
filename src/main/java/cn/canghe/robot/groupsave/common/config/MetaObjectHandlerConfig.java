package cn.canghe.robot.groupsave.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 *
 * @author canghe
 * @date 2019-03-04 2:58 PM
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        Object createTime = getFieldValByName("createdAt", metaObject);
        Object updateTime = getFieldValByName("updatedAt", metaObject);
        if (createTime == null) {
            setFieldValByName("createdAt", new Date(), metaObject);
        }

        if (updateTime == null) {
            setFieldValByName("updatedAt", new Date(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updatedAt", metaObject);
        setFieldValByName("updatedAt", new Date(), metaObject);
    }
}
