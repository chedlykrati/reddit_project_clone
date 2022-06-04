package com.example.reddit.controller;


import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){

        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration Successful" , HttpStatus.OK);

    }

}
