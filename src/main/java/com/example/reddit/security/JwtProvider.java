package com.example.reddit.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;   //JwtEncoder class which will sign the token
    //@Value("3600000")
    @Value("${jwt.expiration.time}")
    private  Long jwtExpirationInMillis;


    //generateToken take an authentication object as input parameter
    public String generateToken(Authentication authentication){
        //User principal = (User) authentication.getPrincipal(); //we want user information to create JWT
        //User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        return generateTokenWithUserName(principal.getName());
    }

    public String generateTokenWithUserName(String username) {
        //JwtClaimsSet or PAYLOAD:DATA
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope" , "ROLE_USER")
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        //jwtEncoder take the claims to pass it to encode after claims encode we retrieve the  method by calling
        // the method getTokenValue and this will generate the json web token in tha background
    }

    public Long getJwtExpirationInMillis() {

        return jwtExpirationInMillis;

    }

}
