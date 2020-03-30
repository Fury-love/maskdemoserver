package com.mask.maskdemoserver.domains.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-12 13:34
 * @Description: 角色实体类
 */
@TableName("role")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -1518804591999270958L;
}
