package server.mainproject.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import server.mainproject.auth.jwt.JwtTokenizer;
import server.mainproject.auth.utils.CustomAuthorityUtils;
import server.mainproject.auth.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    private final CustomAuthorityUtils authorityUtils;

    public JwtVerificationFilter(
            JwtUtils jwtUtils,
            CustomAuthorityUtils authorityUtils) {
        this.jwtUtils = jwtUtils;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("# JwtVerificationFilter");

        try {
            Map<String, Object> claims = jwtUtils.getJwsClaimsFromRequest(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

//        try {
//            // 쿠키에서 AccessToken 추출
//            String accessToken = extractAccessTokenFromCookie(request);
//
//            // AccessToken 검증
//            if (accessToken != null) {
//                Map<String, Object> claims = jwtUtils.getJwsClaimsFromRequest(request);
//                setAuthenticationToContext(claims);
//            }
//        } catch (SignatureException se) {
//            request.setAttribute("exception", se);
//        } catch (ExpiredJwtException ee) {
//            request.setAttribute("exception", ee);
//        } catch (Exception e) {
//            request.setAttribute("exception", e);
//        }
//
//        filterChain.doFilter(request, response);
//    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String authorization = request.getHeader("Authorization");
        String cookie = request.getHeader("Cookie");

        return ((cookie == null ));
//        return (authorization == null || !authorization.startsWith("Bearer")) && (cookie == null || !cookie.startsWith("Authorization"));
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String email = (String) claims.get("email");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue().replace("Bearer_", "");
                }
            }
        }
        return null;
    }



}
