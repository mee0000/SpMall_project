package com.example.spmall.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JwtUtils
 *
 * @author toy aa@nfda
 * @version 2023/10/14 11:15 AM
 * @since JDK11
 **/
public class JwtUtils {
    private static String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey20230327";
    private static Long expire = 4320000L;

    public static String generateJwt(Map<String, Object> claims) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + expire);

        byte[] secretBytekey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretBytekey, SignatureAlgorithm.HS256.getJcaName());

        JwtBuilder builder = Jwts.builder().setClaims(claims).setExpiration(expTime).signWith(signKey, SignatureAlgorithm.HS256);
        return builder.compact();
    }

    /**
     * tonken 해석
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        byte[] secretBytekey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretBytekey, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
    }
}
