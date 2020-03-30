package com.mask.maskdemoserver.domains;

import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.service.user_service.UserService;
import com.mask.maskdemoserver.utils.JWTUtils;
import com.mask.maskdemoserver.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @Author: marico.lv
 * @CreateTime: 2020-03-24 08:28
 * @Description:
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错<br/>
     * 设置realm支持的authenticationToken类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtils.getSubject(principals.toString());
        String sessionId = JWTUtils.getId(principals.toString());
        User loginUser = userService.findByUserName(username);
        // 角色列表
        Set<String> roles;
        // 功能列表
        Set<String> menus;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限

        if (!StringUtils.isNull(loginUser)) {
//            if (loginUser.isAdmin()) {
//                info.addRole("admin");
//                info.addStringPermission("*:*:*");
//            } else {
//                menus = sysMenuService.selectPermsByUserId(loginUser.getId());
//                roles = sysRoleService.selectRoleKeys(loginUser.getId());
//                // 角色加入AuthorizationInfo认证对象
//                info.setRoles(roles);
//                // 权限加入AuthorizationInfo认证对象
//                info.setStringPermissions(menus);
//            }
        }


        return info;
    }

    /**
     * 登陆认证
     *
     * @param auth jwtFilter传入的token
     * @return 登陆信息
     * @throws AuthenticationException 未登陆抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtils.getSubject(token);
        String sessionId = JWTUtils.getId(token);
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("token 无效");
        }
        User loginUser = userService.findByUserName(username);

        if (loginUser == null) {
            throw new AuthenticationException("用户未登录");
        }

        //校验token合法性和redis是否存在
        try {
            if (JWTUtils.verify(token, loginUser)) {
                return new SimpleAuthenticationInfo(token, token, getName());
            } else {
                throw new AuthenticationException("token 验证失败");
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        return super.getAuthorizationInfo(principals);
    }
}

