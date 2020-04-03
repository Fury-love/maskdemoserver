package com.mask.maskdemoserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mask.maskdemoserver.utils.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 *
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 11:59
 * @Description:
 */

@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdTime", DateUtils.getTimeNow(), metaObject);
        this.setFieldValByName("updatedTime", DateUtils.getTimeNow(), metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime", DateUtils.getTimeNow(), metaObject);

    }

}

