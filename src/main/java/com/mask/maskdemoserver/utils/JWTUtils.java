package com.mask.maskdemoserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import com.auth0.jwt.algorithms.Algorithm;
import com.mask.maskdemoserver.domains.po.User;
import org.springframework.stereotype.Component;

import static com.mask.maskdemoserver.utils.CommonUtils.encryptToMD5;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 15:15
 * @Description:
 */
@Component
public class JWTUtils {
    // 过期时间 24 小时
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;

    // 过期时间 一年
    private static final long EXPIRE_TIME_ONE_YEAR = 60 * 24 * 60 * 1000 * 365;

    /**
     * 记住密码时token的过期时间，单位分钟<br/>
     * 默认15天
     */
    private static final long REMBERME_TOKEN_EXPIRETIME = 60 * 24 * 15L;



    /**
     * 生成签名
     *
     * @param user
     * @param sessionId
     * @return
     */
    public static String sign(User user, String sessionId) {
        long currentTimeMillis = System.currentTimeMillis();
        // 指定过期时间，过期时间可以重新刷新token
        Date date = new Date(currentTimeMillis + EXPIRE_TIME);
        String password = "";
        try {
            password = encryptToMD5(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Algorithm algorithm = Algorithm.HMAC256(password);
        return JWT.create()
                .withClaim("username", user.getLoginName())//过期时间
//                .withClaim(SESSION_ID, sessionId)
//                .withClaim(REFRESH_TOKEN_EXPIRE_TIME, currentTimeMillis)
//                .withClaim(TOKEN_EXPIRE_TIME, tokenExpireTime)
                .withIssuedAt(new Date(currentTimeMillis))//签发时间
                .withIssuer("sys")//签发者
                .withJWTId(sessionId)//jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                .withSubject(user.getLoginName())// jwt所面向的用户
                .withExpiresAt(date)//token 超时时间
                .withAudience("")//颁发给谁
                .withNotBefore(new Date(currentTimeMillis))//在此时间前不可用
                .sign(algorithm);
    }

    /**
     * 校验 token
     * @param token
     * @param user
     * @return
     */
    public static boolean verify(String token, User user){
        String password = "";
        try {
            password = encryptToMD5(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Algorithm algorithm = Algorithm.HMAC256(password);
        JWTVerifier verifier =JWT.require(algorithm)
                .withClaim("username",user.getLoginName())
                .withSubject(user.getLoginName())
                .build();
        verifier.verify(token);
        return true;
    }

    public static String getUserName(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 获取id
     *
     * @param token token签名
     * @return id
     */
    public static String getId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getId();
    }

    /**
     * 获取主题
     *
     * @param token token签名
     * @return 主题
     */
    public static String getSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    /**
     * 获取token可刷新时间
     *
     * @param token token
     * @return 可刷新时间
     */
    public static Long getTokenExpireTime(String token) {
        Claim claim = getClaim(token, "tokenExpireTime");
        return claim == null ? null : claim.asLong();
    }

    /**
     * 获取认证信息
     *
     * @param token token
     * @param claim claim
     * @return 认证信息
     */
    private static Claim getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取token签发时间
     *
     * @param token token签名
     * @return 签发时间
     */
    public static Date getIssuedAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }
}
