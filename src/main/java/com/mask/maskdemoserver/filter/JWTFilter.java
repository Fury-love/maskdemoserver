package com.mask.maskdemoserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mask.maskdemoserver.common.Constants;
import com.mask.maskdemoserver.common.ResponseMessage;
import com.mask.maskdemoserver.domains.JWTToken;
import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.service.token.TokenService;
import com.mask.maskdemoserver.service.user_service.UserService;
import com.mask.maskdemoserver.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.mask.maskdemoserver.common.ResponseStatus.FAILD_401;

/**
 * token认证过滤器
 * @Author: marico.lv
 * @CreateTime: 2020-03-24 10:46
 * @Description:
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

        private static final String CHARSET = "UTF-8";
        private TokenService tokenService;

        @Autowired
        private UserService userService;

        public JWTFilter(TokenService tokenService) {
            this.tokenService = tokenService;
        }


        /**
         * 检测用户是否登录
         * 检测header里面是否包含Authorization字段即可
         *
         * @param request  请求
         * @param response 响应
         * @return 是否登录
         */
        @Override
        protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
            return StringUtils.isNotEmpty(WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER));
        }

        /**
         * 执行登录方法(由自定义realm判断,吃掉异常返回false)
         */
        @Override
        protected boolean executeLogin(ServletRequest request, ServletResponse response) {
            String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isEmpty(token)) {
                String msg = "executeLogin method token must not be null";
                throw new IllegalStateException(msg);
            }

            JWTToken jwtToken = new JWTToken(token);

            this.getSubject(request, response).login(jwtToken);
            return true;
        }

        @Override
        protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
            if (this.isLoginAttempt(request, response)) {
                try {
                    this.executeLogin(request, response);
                    return true;
                } catch (Throwable e) {
                    String message = e.getMessage();
                    Throwable throwable = e.getCause();
                    if (throwable instanceof TokenExpiredException) {
                        // AccessToken已过期
                        if (this.refreshToken(request, response)) {
                            return true;
                        }
                    } else {
                        log.error("用户token信息异常：{}", message);
                    }
                }
            }

            return false;
        }

        /**
         * 如果这个Filter在之前isAccessAllowed()方法中返回false,则会进入这个方法。我们这里直接返回错误的response,说明登录认证失败了
         *
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            this.response401(response);
            return false;
        }

        /**
         * 无需转发，直接返回Response信息
         */
        private void response401(ServletResponse response) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            String contentType = "application/json;charset=" + CHARSET;
            httpResponse.setCharacterEncoding(CHARSET);
            httpResponse.setStatus(401);
            httpResponse.setContentType(contentType);

            try {
                ServletOutputStream outputStream = httpResponse.getOutputStream();
                ResponseMessage responseMessage = new ResponseMessage();
                responseMessage.setCode(FAILD_401.getCode());
                responseMessage.setMessage(FAILD_401.getMessage());
                outputStream.write(JSONObject.toJSONString(responseMessage).getBytes());
                outputStream.close();
            } catch (IOException e) {
                log.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            }
        }


        /**
         * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
         */
        private boolean refreshToken(ServletRequest request, ServletResponse response) {
            // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
            String token = this.getAuthzHeader(request);
            // token 过期时间
            Long tokenExpireTime = JWTUtils.getTokenExpireTime(token);
            //token 签发时间
            Date issuedAt = JWTUtils.getIssuedAt(token);
            //如果没有超过token超时时间，那么对token进行刷新
            //如果超过了token超时时间，那么则提示用户登录超时

            //当前时间-签发时间 <= token允许刷新的时间
            if (tokenExpireTime != null && (System.currentTimeMillis() - issuedAt.getTime() <= tokenExpireTime * 60 * 1000)) {
                // 在可刷新时间范围内

                //查看token可刷新时间是否更改
                User user = userService.findByUserName(JWTUtils.getUserName(token));
                String newToken = tokenService.refreshToken(user);

                if (StringUtils.isNoneEmpty(newToken)) {
                    // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
                    JWTToken jwtToken = new JWTToken(newToken);
                    this.getSubject(request, response).login(jwtToken);
                    // 设置响应的Header头新Token
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader(AUTHORIZATION_HEADER, newToken);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", Constants.AUTHORIZATION);
                    return true;
                }
            }
            return false;
        }
    }
