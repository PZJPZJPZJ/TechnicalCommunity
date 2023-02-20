package com.pzj.technicalcommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;

import java.util.Date;

@Data
public class JwtUtils {

    private static final long EXPIRE_TIME = 120 * 60 * 1000; // 过期时间 120 分钟
    private static final String SECRET = "f5hu8kr32xh9"; // 密钥

    /**
     * 生成 token
     * @param userId 用户 ID
     * @return 返回生成的 token 字符串
     */
    public static String generateToken(String userId) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        return JWT.create()
                .withIssuer("auth0")
                .withSubject("login_token")
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate)
                .withClaim("user_id", userId)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 从 token 中解析出用户信息
     * @param token JWT 令牌
     * @return 返回解析出的用户 ID
     */
    public static String getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("user_id").asString();
        } catch (JWTDecodeException e) {
            // 解码失败，返回 null
            return null;
        }
    }
}
