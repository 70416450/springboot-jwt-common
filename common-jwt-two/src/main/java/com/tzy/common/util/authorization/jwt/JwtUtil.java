package com.tzy.common.util.authorization.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */

@Component
public class JwtUtil {

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey() {
        String stringKey = "tzy" + Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 创建jwt
     *
     * @return
     * @throws Exception
     */
    public static String createJWT(String userString, String permString) throws Exception {
        SecretKey secretKey = generalKey();
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)//header
                .withClaim("user", userString)
                .withClaim("perm", permString)
                .sign(Algorithm.HMAC256(secretKey.getAlgorithm()));//加密
        return token;
    }

    /**
     * 解密jwt
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> parseJWT(String token) throws UnsupportedEncodingException {
        SecretKey key = generalKey();
        String algorithm = key.getAlgorithm();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(algorithm))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        return claims;
    }

}

