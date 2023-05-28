package server.mainproject.auth.utils;


import io.jsonwebtoken.JwtException;
import server.mainproject.auth.jwt.JwtTokenizer;
import server.mainproject.exception.BusinessLogicException;
import server.mainproject.exception.ExceptionCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class JwtUtils {
    private final JwtTokenizer jwtTokenizer;

    public JwtUtils(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    public Map<String, Object> getJwsClaimsFromRequest(HttpServletRequest request) {

        String jws = request.getHeader("Authorization").replace("Bearer_", "");

//        accessToken = accessToken.replace("Bearer_", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        try {
            Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
//            Map<String, Object> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody();
            return claims;
        } catch (JwtException e) {

            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESSTOKEN);
        }
    }

    public Map<String, Object> getJwsClaimsFromAccessToken(String accessToken) {

//        accessToken = accessToken.replace("Bearer_", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        try {
            Map<String, Object> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody();
//            Map<String, Object> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody();
//            return claims;
            return claims;
        } catch (JwtException e) {

            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESSTOKEN);
        }
    }
}

