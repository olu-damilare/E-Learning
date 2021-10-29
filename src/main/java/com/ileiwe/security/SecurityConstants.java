package com.ileiwe.security;


import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

    @Value("${secret}")
    public static String SECRET;
    @Value("${expiration.time}")
    public static long EXPIRATION_TIME; //10 DAYS
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
}
