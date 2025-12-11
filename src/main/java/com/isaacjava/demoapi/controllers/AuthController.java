package com.isaacjava.demoapi.controllers;

import com.isaacjava.demoapi.config.jwt.JwtGenerator;
import com.isaacjava.demoapi.dto.JwtAuthResponseDto;
import com.isaacjava.demoapi.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new JwtAuthResponseDto(token), HttpStatus.OK);
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtGenerator.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Implementar según sea necesario
    }

    public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(jwtGenerator.getSigningKey())
                        .build()
                        .parseClaimsJws(token); // If no exception is thrown, token is valid
                return true;
            } catch (io.jsonwebtoken.MalformedJwtException e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                System.out.println("JWT token is expired: " + e.getMessage());
            } catch (io.jsonwebtoken.UnsupportedJwtException e) {
                System.out.println("JWT token is unsupported: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("JWT claims string is empty: " + e.getMessage());
            } catch (io.jsonwebtoken.security.SignatureException e) {
                System.out.println("Signature validation failed: " + e.getMessage());
            }
            return false;
        }
}
