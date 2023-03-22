package cn.canghe.robot.groupsave.common.util;

/**
 * @author bin
 * @date 2020/08/21 12:48:59
 * @description 描述信息
 * @menu
 */

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final Logger log = LoggerFactory.getLogger(JWTUtils.class);
    public static final String JWT_SECRET = "ZGZuaMRmBiUyMGRbmZkc2hzZGhzZGhzZA";
    public static int expirationDays = 14;

    public JWTUtils() {
    }

    public static String createJWT(Map map) {
        Instant now = Instant.now();
        return createJWT(map, Date.from(now.plus((long)expirationDays, ChronoUnit.DAYS)));
    }

    public static String createJWT(Map map, Date expiration) {
        Map<String, Object> mapHeahder = new HashMap(16);
        mapHeahder.put("alg", "HS256");
        mapHeahder.put("typ", "JWT");
        Instant now = Instant.now();
        String jwt = Jwts.builder().setIssuedAt(Date.from(now)).setClaims(map).setHeader(mapHeahder).setExpiration(expiration).signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode("ZGZuaMRmBiUyMGRbmZkc2hzZGhzZGhzZA")).compact();
        return Base64.getEncoder().encodeToString(jwt.getBytes());
    }

    public static boolean verify(String jwtToken) {
        try {
            byte[] b = Base64.getDecoder().decode(jwtToken);
            String base64jwt = new String(b);
            Jwts.parser().setSigningKey(TextCodec.BASE64.encode("ZGZuaMRmBiUyMGRbmZkc2hzZGhzZGhzZA")).parse(base64jwt);
            return true;
        } catch (ExpiredJwtException var3) {
            log.error(var3.getMessage(), var3);
        } catch (MalformedJwtException var4) {
            log.error(var4.getMessage(), var4);
        } catch (SignatureException var5) {
            log.error(var5.getMessage(), var5);
        } catch (Exception var6) {
            log.error(var6.getMessage(), var6);
        }

        return false;
    }

    public static Map getJWTData(String jwtToken) {
        try {
            byte[] b = Base64.getDecoder().decode(jwtToken);
            String base64jwt = new String(b);
            Jwt jwt = Jwts.parser().setSigningKey(TextCodec.BASE64.encode("ZGZuaMRmBiUyMGRbmZkc2hzZGhzZGhzZA")).parse(base64jwt);
            return (Map)jwt.getBody();
        } catch (ExpiredJwtException var4) {
            log.error(var4.getMessage(), var4);
        } catch (MalformedJwtException var5) {
            log.error(var5.getMessage(), var5);
        } catch (SignatureException var6) {
            log.error(var6.getMessage(), var6);
        } catch (Exception var7) {
            log.error(var7.getMessage(), var7);
        }

        return null;
    }

    public static Map parseJWTData(String jwtToken) {
        byte[] b = Base64.getDecoder().decode(jwtToken);
        String base64jwt = new String(b);
        Jwt jwt = Jwts.parser().setSigningKey(TextCodec.BASE64.encode("ZGZuaMRmBiUyMGRbmZkc2hzZGhzZGhzZA")).parse(base64jwt);
        return (Map)jwt.getBody();
    }

    public static String refreshToken(String token) {
        return verify(token) ? createJWT(getJWTData(token)) : null;
    }
}
