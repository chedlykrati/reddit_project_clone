package com.example.reddit.service;


import com.example.reddit.dto.AuthenticationResponse;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RefreshTokenRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.model.NotificationEmail;
import com.example.reddit.model.User;
import com.example.reddit.model.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import com.example.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

//spring recommands to use constructor @AllArgsConstructor instead of @Autowired (injection)

    //@Autowired
    private final PasswordEncoder passwordEncoder;
    //@Autowired
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    //@Transactional annotation is the metadata that specifies the semantics of the transactions on a method. We have two ways to rollback a transaction: declarative and programmatic.
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);


        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account" ,
                user.getEmail(), "thank you for sign up to Reddit " +
                "Please click on the below url to activate account :" +
                "http://localhost:8085/api/auth/accountVerification/" + token));


    }

    private String generateVerificationToken(User user) {
        //generate unique random 128 bit value which we can use as our verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;

    }


    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new SpringRedditException("Invalid Token !"));
        fetchUserAndEnable(verificationToken.get());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken) {

        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("user not found - " + username));
        user.setEnabled(true);
        userRepository.save(user);

    }


    public AuthenticationResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername() ,
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        //return new AuthenticationResponse(token, loginRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public User getCurrentUser() {

        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(()->new UsernameNotFoundException("Username not found " + principal));

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
       refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
       String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
       return AuthenticationResponse.builder()
               .refreshToken(refreshTokenRequest.getRefreshToken())
               .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
               .username(refreshTokenRequest.getUsername())
               .build();


    }
}
