package com.pzj.technicalcommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;

@Data
public class JwtUtils {

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 过期时间一天(h*m*s*ms)
    private static final String SECRET = "f5hu8kr32xh9"; // 密钥

    /**
     * 生成 token
     */
    public static String generateToken(String userId) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 从 token 中解析出用户信息
     */
    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证 JWT Token 是否过期
     * @param token
     * @return true过期，false有效
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
