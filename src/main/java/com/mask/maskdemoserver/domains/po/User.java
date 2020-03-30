package com.mask.maskdemoserver.domains.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mask.maskdemoserver.domains.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/3/21
 * Time: 14:37
 */
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = -7919948187678229468L;

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    /**
     * 用户登录名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 用户登录名
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 用户登录密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户状态
     */
    @TableField("status")
    private int status;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 用户性别
     */
    @TableField("gender")
    private int gender;
    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 微信用户openid
     */
    @TableField("wxid")
    private int wxid;
    /**
     * 收益
     */
    @TableField("total_reward")
    private BigDecimal totalReward;
    /**
     * 佣金
     */
    @TableField("reward")
    private BigDecimal reward;
    /**
     * 积分
     */
    @TableField("credit")
    private String credit;
    /**
     * 生日
     */
    @TableField("birth")
    private Date birth;
    /**
     * 邮件
     */
    @TableField("email")
    private String email;
    /**
     * 省
     */
    @TableField("province")
    private String province;
    /**
     * 市
     */
    @TableField("city")
    private String city;
    /**
     * 区
     */
    @TableField("county")
    private String county;

}
