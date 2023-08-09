package com.dev.springboottesting.entity;

import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Token {

    public static final String API_SECRET_KEY = "usertokenkey";
    public static final int TOKEN_EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_REG_TOKEN"))
    private User user;

    public Token(User user, String token){
        super();
        this.user = user;
        this.token = generateToken(user);
        this.expirationTime = calculateExpirationTime(TOKEN_EXPIRATION_TIME);
    }

    public Token(User user){
        super();
        this.user = user;
        this.token = generateToken(user);
        this.expirationTime = calculateExpirationTime(TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(User user){
        try {
            long time = System.currentTimeMillis();
            String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Token.API_SECRET_KEY)
                    .setIssuedAt(new Date())
                    .setExpiration(calculateExpirationTime(TOKEN_EXPIRATION_TIME))
                    .claim("id", user.getId())
                    .claim("firstName", user.getFirstName())
                    .claim("lastName", user.getLastName())
                    .compact();

            return token;
        }catch (Exception e){
            throw new UserNotFoundException("Bad request");
        }
    }

    private Date calculateExpirationTime(long tokenExpirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(calendar.MINUTE, (int) tokenExpirationTime);
        return new Date(calendar.getTime().getTime());
    }


}
