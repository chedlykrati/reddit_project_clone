package com.example.reddit.controller;


import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully !" , HttpStatus.OK);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){

        authService.login(loginRequest);

    }


}
