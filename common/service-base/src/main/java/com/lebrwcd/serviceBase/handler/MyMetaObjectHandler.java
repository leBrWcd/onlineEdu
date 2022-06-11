package com.lebrwcd.serviceBase.handler;/**
 * @author lebrwcd
 * @date 2022/4/25
 * @note
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName MyMetaObjectHandler
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/25
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //数据库中的是gmt_create,java字段为gmtCreate
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
