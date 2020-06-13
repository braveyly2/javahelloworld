package com.hust.utils;

import io.jsonwebtoken.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 慌途L
 */
public class JwtUtilsHelper {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtilsHelper.class);

    private static final String SECRET_KEY = "adsfkoemejn13443#@^*(%$-=4=+*&%feadsfkoemejn13443#@^*(%$-=4=+*&%fe";
    private static final String SECRET_KEY_REF = "adsfkoemejn13443#@^*(%$-=4=+*&%feadsfkoemejn13443#@^*(%$-=4=+*&%fe";

    private static Integer ACCESS_EXPIRES = 60 * 60 * 4;  //access_token有效时间,4hour
    private static Integer REFRESH_EXPIRES = 60 * 60 * 8;  //refresh_token有效时间,8hour

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;//指定签名的时候使用的签名算法

    /**
     *  获取加密key
     * @param keys     token加解密的key
     * @return
     */
    private static Key getEncryptedKey(String keys){
        Key key = new SecretKeySpec(keys.getBytes(), signatureAlgorithm.getJcaName());
        return key;
    }

    /**
     * 生成Jwt Token
     * @param data          JWT payload负载（自己需要封装的参数放这里,json格式的字符串）
     * @param expiresDate   过期时间
     * @param secretKey     签名的秘钥，和生成的签名的秘钥一模一样
     * @return
     */
    private static String createToken(String data, Integer expiresDate, String secretKey){
        /** header  BEGIN */
        Map<String,Object> header = new HashMap<String, Object>();//JWT header头部
        long time = new Date().getTime();
        header.put("alg","HS512-2");//表示签名的算法，默认也是这个，可选（header里面的字段也全是一些弱信息）
        header.put("iat", time/1000);//签发时间（单位均为utc时间的秒）
        header.put("exp", (time/1000) + expiresDate);//过期时间
        //header.put("exp222", (time/1000) + expiresDate);//header实际上也是可以自定义的
        /** header  END */

        JwtBuilder builder = Jwts.builder()
                .setHeader(header)          //new一个JwtBuilder，设置jwt的body
                .setPayload(data)         //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的payload赋值，
                // 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .signWith(signatureAlgorithm, getEncryptedKey(secretKey));//设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();           //开始压缩为xxx.xxx.xxx格式的jwt
    }

    /**
     * 解密Jwt得到payload负载的数据
     * @param jwt
     * @param secretKey     签名的秘钥，和生成的签名的秘钥一模一样
     * @return
     */
    private static Claims decodeTokenPart(String jwt, String secretKey){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getEncryptedKey(secretKey))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (Exception e) {
            logger.debug("validate is Jwt token payload error ：", e);
            return null;
        }
    }

    /**
     * 解密Jwt得到全部数据
     * @param jwt
     * @param secretKey     签名的秘钥，和生成的签名的秘钥一模一样
     * @return
     */
    private static Jwt decodeTokenAll(String jwt, String secretKey){
        try {
            Jwt parse = Jwts.parser()
                    .setSigningKey(getEncryptedKey(secretKey))
                    .parse(jwt);
            return parse;
        } catch (Exception e) {
            logger.debug("validate is Jwt token error ：", e);
            return null;
        }
    }


    /**
     * 生成Jwt token
     * @param data   JWT payload负载
     * @return
     */
    public static String accessTonken(String data) {
        return createToken(data, ACCESS_EXPIRES, SECRET_KEY);
    }

    /**
     * 生成Jwt token
     * @param data   JWT payload负载
     * @return
     */
    public static String refreshToken(String data) {
        return createToken(data, REFRESH_EXPIRES, SECRET_KEY_REF);
    }

    /**
     * 解密JWT  （得到包含header，payload，signature全部数据）
     * @param jwt
     * @return
     */
    public static Jwt accessTonkenDecodeAll(String jwt) {
        return decodeTokenAll(jwt, SECRET_KEY);
    }

    /**
     * 解密JWT  （得到包含header，payload，signature全部数据）
     * @param jwt
     * @return
     */
    public static Jwt refreshTokenDecodeAll(String jwt) {
        return decodeTokenAll(jwt, SECRET_KEY_REF);
    }


    /**
     * 解密jwt    (得到payload部分的数据)
     *
     * @param jwt
     * @return
     */
    public static Claims accessTonkenDecodePart(String jwt) {
        return decodeTokenPart(jwt, SECRET_KEY);
    }

    /**
     * 解密jwt    (得到payload部分的数据)
     *
     * @param jwt
     * @return
     */
    public static Claims refreshTokenDecodePart(String jwt){
        return decodeTokenPart(jwt, SECRET_KEY_REF);
    }


    /**
     * 判断token是否过期
     *
     * @param expiration    token过期时间
     * @return true：过期  false：未过期
     */
    public static boolean isTokenExpired(Long expiration){
        long nowDate = new Date().getTime() / 1000;
        return (nowDate > expiration) ? true : false;
    }

    public static boolean verify(String token, String data, String secretKey) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("user_id",169061)
                    .withClaim("user_name","admin")
                    .withClaim("ip","192.168.110.555")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static void main(String[] args) {

        /** payload  BEGIN */
        int userId = 169061;
        String userName = "admin";
        String ip = "192.168.110.555";
        Map<String,Object> payload = new HashMap<String, Object>();
        payload.put("user_id",userId);
        payload.put("user_name",userName);
        payload.put("ip",ip);
        /** payload  END */


        //得到jwt
        String jwt = accessTonken(JSONObject.fromObject(payload).toString());
        String jwt2 = refreshToken(JSONObject.fromObject(payload).toString());
        System.out.println(jwt);
        System.out.println(jwt2);
        System.out.println("\n");
        if(verify(jwt, JSONObject.fromObject(payload).toString(), SECRET_KEY)){
            System.out.println("verify pass for token:" + jwt);
        }

        //解析jwt
        Jwt parseJwt = accessTonkenDecodeAll(jwt);//四小时过期
        Jwt parseJwt2 = refreshTokenDecodeAll(jwt2);//八小时过期

        System.out.println("parseJwt=="+parseJwt);

        System.out.println("parseJwt2=="+parseJwt2);

        Claims claims1 = accessTonkenDecodePart(jwt);
        Claims claims2 = accessTonkenDecodePart(jwt2);

        System.out.println("claims1=="+claims1);

        System.out.println("claims2=="+claims2);
    }
}

/*
eyJleHAiOjE1OTE2OTg1MTMsImFsZyI6IkhTNTEyIiwiaWF0IjoxNTkxNjg0MTEzfQ.eyJ1c2VyX2lkIjoxNjkwNjEsInVzZXJfbmFtZSI6ImFkbWluIiwiaXAiOiIxOTIuMTY4LjExMC41NTUifQ.Cu9Q00HtDX_izJvzUeFiTzBbRaCn0RQIoE1Ghq2biCUO6IeQGkPdiXXrd8pl7oFBAh84Lm9qoBQTbhQmnWPnMQ
eyJleHAiOjE1OTE3MTI5MTQsImFsZyI6IkhTNTEyIiwiaWF0IjoxNTkxNjg0MTE0fQ.eyJ1c2VyX2lkIjoxNjkwNjEsInVzZXJfbmFtZSI6ImFkbWluIiwiaXAiOiIxOTIuMTY4LjExMC41NTUifQ.P0idtcAl1po9oOL6ZiQCulpH-bhMW16OsKAIk5bkiNG1JhLkZU7dnRk9u_S3yRrt_uVhhL0nDujT7PmO6iE6Dw


parseJwt==header={exp=1591698513, alg=HS512, iat=1591684113},body={user_id=169061, user_name=admin, ip=192.168.110.555},signature=Cu9Q00HtDX_izJvzUeFiTzBbRaCn0RQIoE1Ghq2biCUO6IeQGkPdiXXrd8pl7oFBAh84Lm9qoBQTbhQmnWPnMQ
parseJwt2==header={exp=1591712914, alg=HS512, iat=1591684114},body={user_id=169061, user_name=admin, ip=192.168.110.555},signature=P0idtcAl1po9oOL6ZiQCulpH-bhMW16OsKAIk5bkiNG1JhLkZU7dnRk9u_S3yRrt_uVhhL0nDujT7PmO6iE6Dw
claims1=={user_id=169061, user_name=admin, ip=192.168.110.555}
claims2=={user_id=169061, user_name=admin, ip=192.168.110.555}
 */