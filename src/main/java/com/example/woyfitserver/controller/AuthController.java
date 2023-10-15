package com.example.woyfitserver.controller;

import com.example.woyfitserver.auth.JwtUtil;
import com.example.woyfitserver.auth.SignUpRequest;
import com.example.woyfitserver.user.User;
import com.example.woyfitserver.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {


    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String test(){
        return "Test";
    }
    @PostMapping(path = "/sign-up")
    public ResponseEntity<?> SignUp(@RequestBody SignUpRequest signUpRequest){
//        UserDetails userDetails = userService.loadUserByUsername(signUpRequest.getUsername());

            User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());
////            Set<Role> roles = new HashSet<>();
            userService.saveUser(user);
            String token = jwtUtil.createToken(new User(signUpRequest.getUsername(), signUpRequest.getPassword()));
            return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> SignIn(){
//        System.out.println((UserDetails)authentication.getPrincipal());
        return ResponseEntity.ok("Signin");
    }

}
