package com.ileiwe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.ileiwe.security.SecurityConstants.*;


@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

//    @Autowired
//    SecurityConstants constants;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext()
                            .setAuthentication(getAuthentication(request));

        chain.doFilter(request, response);


    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        log.info("Token --> {}", token);
        if(token != null ){
            String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                                .build()
                                .verify(token.replace(TOKEN_PREFIX, ""))
                                .getSubject();

            if(username != null){
                return new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>()
                );
            }

        }
        return null;

    }
}
