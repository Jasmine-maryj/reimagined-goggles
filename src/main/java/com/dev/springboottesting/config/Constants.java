package com.dev.springboottesting.config;

import com.dev.springboottesting.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String API_SECRET_KEY = "usertokenkey";
    public static final long TOKEN_EXPIRATION_TIME = 10 * 60 * 1000;

}
