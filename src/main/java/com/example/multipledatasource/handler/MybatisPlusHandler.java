package com.example.multipledatasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 公共字段填充
 *
 * @author Wang Junwei
 * @date 2022/4/12 11:50
 */
public class MybatisPlusHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (null == this.getFieldValByName("createTime", metaObject)) {
            this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        }
        this.strictInsertFill(metaObject, "createUser", Integer.class, 1111);
        if (null == this.getFieldValByName("updateTime", metaObject)) {
            this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        }
        this.strictInsertFill(metaObject, "updateUser", Integer.class, 1111);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (null == this.getFieldValByName("updateTime", metaObject)) {
            this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        }
        this.strictInsertFill(metaObject, "updateUser", Integer.class, 1111);
    }
}
