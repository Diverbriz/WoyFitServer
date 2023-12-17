package com.example.woyfitserver.security;

import com.example.woyfitserver.auth.JwtUtil;
import com.example.woyfitserver.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if(authToken != null && authToken.startsWith("Bearer ")){
            jwtToken = authToken.substring(7);

            try {
                username = jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            try{
                UserDetails userDetails = userService.loadUserByUsername(username);
                if(jwtUtil.validateToken(jwtToken, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (Exception e){
                System.out.println("Error load user by username: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}