package com.ileiwe.security;


import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

//    @Value("${secret}")
    public static String SECRET = "864000000";
//    @Value("${expiration.time}")
    public static long EXPIRATION_TIME = 864000000; //10 DAYS
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
