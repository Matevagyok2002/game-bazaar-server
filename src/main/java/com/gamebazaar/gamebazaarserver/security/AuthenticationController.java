package com.gamebazaar.gamebazaarserver.security;

import com.gamebazaar.gamebazaarserver.database.entities.ContactInfo;
import com.gamebazaar.gamebazaarserver.database.entities.User;
import com.gamebazaar.gamebazaarserver.database.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
@CrossOrigin
public class AuthenticationController {

    public final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    private static final String AUTH_TOKEN_HEADER_NAME = "Authentication";
    private static TokenManager tokens = new TokenManager();

    public Gson gson = new Gson();

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("X_Username") String username,
                                        @RequestHeader("X_Password") String password){
        if (userService.checkCredentials(username, password)) {
            String token = tokenGenerator();
            tokens.addToken(token);
            return ResponseEntity.ok(token);
        }
        else
            return ResponseEntity.status(401).body("Error: Incorrect credentials.");
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken(@RequestParam String token){
        System.out.println(tokens.getTokens().containsKey(token));
        if (tokens.getTokens().containsKey(token))
            return ResponseEntity.ok("active");
        else
            return ResponseEntity.status(401).body("expired");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<String> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(gson.toJson(userService.findByUsername(username)));
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader(name = AUTH_TOKEN_HEADER_NAME) String token){
        tokens.deleteToken(token);
        return ResponseEntity.ok("");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        userService.create(user);
        return ResponseEntity.ok("");
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody User user){
        userService.update(user);
        return ResponseEntity.ok("");
    }

    public String tokenGenerator(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[40];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (token == null || !tokens.getTokens().containsKey(token)) {
            throw new BadCredentialsException("");
        }
        return new ApiKeyAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
    }
}