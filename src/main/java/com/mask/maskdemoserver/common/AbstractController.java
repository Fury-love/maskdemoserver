package com.mask.maskdemoserver.common;

import com.mask.maskdemoserver.domains.po.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-06 15:30
 * @Description:
 */

public class AbstractController {
    //   获取用户ID
    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }
}
