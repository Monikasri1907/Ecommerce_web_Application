package com.ecommerce.controller;

import com.ecommerce.model.StoreUser;
import com.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repo;
    public AuthController(UserRepository repo){this.repo=repo;}

    @PostMapping("/register") public Map<String,String> register(@RequestBody StoreUser user){
        if(repo.findByEmail(user.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
        StoreUser saved=repo.save(new StoreUser(user.getName(),user.getEmail(),user.getPassword(),"USER"));
        return Map.of("name",saved.getName(),"email",saved.getEmail(),"role",saved.getRole());
    }
    @PostMapping("/login") public Map<String,String> login(@RequestBody LoginRequest request){
        StoreUser user=repo.findByEmail(request.email()).orElseThrow(
            ()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));
        if(!user.getPassword().equals(request.password()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        return Map.of("name",user.getName(),"email",user.getEmail(),"role",user.getRole());
    }
    public record LoginRequest(String email,String password) {}
}
