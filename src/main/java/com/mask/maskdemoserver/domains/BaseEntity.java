package com.mask.maskdemoserver.domains;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础Bean
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 17:18
 * @Description:
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1790210322355989896L;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(value = "updated_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
}
