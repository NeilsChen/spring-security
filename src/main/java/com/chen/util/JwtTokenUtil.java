package com.chen.util;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {

	public static final String KEY_PREFIX = "TOKEN:";
	public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
	
	private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.jks"); // 寻找证书文件
	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;
	
	@Value("${jwt.salt}")
	private String salt;

	static { // 将证书文件里边的私钥公钥拿出来
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS"); // java key store 固定常量
			keyStore.load(inputStream, "chen123456".toCharArray());
			privateKey = (PrivateKey) keyStore.getKey("www.neilschen.com", "chen123456".toCharArray()); // jwt 为 命令生成整数文件时的别名
			publicKey = keyStore.getCertificate("www.neilschen.com").getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param subject 
	 * @param expirationSeconds 过期时间 秒
	 * @param salt 秘钥加密 盐值   
	 * @return
	 */
	public static String generateToken(String subject) {
		return Jwts.builder().setClaims(null).setSubject(subject).setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
				// .signWith(SignatureAlgorithm.HS512, salt) // 不使用公钥私钥
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	/**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
//            		.setSigningKey(salt) // 不使用公钥私钥
            		.setSigningKey(publicKey)
            		.parseClaimsJws(token)
            		.getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
	
    /**
     * 获取用户名
     * @param token
     * @return
     */
	public static String parseToken(String token) {
		String subject = "";
		try {
			subject = getClaimsFromToken(token).getSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

	
	 /**
     * 判断令牌是否过期
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
