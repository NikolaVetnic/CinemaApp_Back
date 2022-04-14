package com.pris.cinema.security;

public class SecurityConstants {

    public static final String ROLE_PREFIX      = "ROLE_";

    public static final String SIGN_UP_URLS     = "/api/users/**";
    public static final String H2_URL           = "h2-console/**";
    public static final String SECRET           = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX     = "Bearer ";
    public static final String HEADER_STRING    = "Authorization";

    public static final long EXPIRATION_TIME    = 300_000;
}
