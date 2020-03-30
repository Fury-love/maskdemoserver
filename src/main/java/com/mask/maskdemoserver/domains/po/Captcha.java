package com.mask.maskdemoserver.domains.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 校验图实体类.
 * User: Marico.lv
 * Date: 2019/5/15
 * Time:
 */
@TableName("captcha")
@Data
public class Captcha implements Serializable {
    private static final long serialVersionUID = 2707537919527250510L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private long id;
    /**
     * uuid
     */
    @TableField("uuid")
    private String uuid;
    /**
     * 校验码
     */
    @TableField("code")
    private String code;
    /**
     * 超时时间
     */
    @TableField("expire_date")
    private Date expireDate;
}
