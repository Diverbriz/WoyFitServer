package com.example.woyfitserver.controller;

import com.example.woyfitserver.auth.JwtResponse;
import com.example.woyfitserver.auth.JwtUtil;
import com.example.woyfitserver.auth.SignInRequest;
import com.example.woyfitserver.auth.SignUpRequest;
import com.example.woyfitserver.user.User;
import com.example.woyfitserver.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public String test(){
        try {
            return "Test";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @PostMapping(path = "/sign-up")
    public ResponseEntity<?> SignUp(@RequestBody SignUpRequest signUpRequest){
          User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());
          userService.saveUser(user);
          String token = jwtUtil.createToken(user);
          JwtResponse tokenResponse = new JwtResponse(token, "");
          Map<String, String> response = new HashMap<>();
          response.put("token", tokenResponse.getType() + " " + token);
          return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> SignIn(@RequestBody SignInRequest signInRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());
        String token = jwtUtil.createToken((User)userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("token", "Bearer " + token);
        return ResponseEntity.ok(response);
    }
}
