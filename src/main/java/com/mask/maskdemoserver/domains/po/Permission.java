package com.mask.maskdemoserver.domains.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-12 13:40
 * @Description: 权限实体类
 */
@TableName("user")
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 61009205694945074L;
}
