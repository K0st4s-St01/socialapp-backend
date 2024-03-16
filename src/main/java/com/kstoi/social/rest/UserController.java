package com.kstoi.social.rest;

import com.kstoi.social.config.auth.AuthenticationManager;
import com.kstoi.social.config.auth.JwtService;
import com.kstoi.social.models.dtos.SUserDto;
import com.kstoi.social.services.SUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private SUserService service;
    private AuthenticationManager authManager;
    private JwtService jwtService;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody SUserDto dto){
        try {
            Map<String, String> result = service.create(dto);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return Map.of("result",e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SUserDto dto){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword()));
        if(authentication == null){
            return ResponseEntity.ok(Map.of("result","login failed"));
        }
        return ResponseEntity.ok(Map.of(
                "token",jwtService.getToken(authentication.getName())
                ,"result","ok"));
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody SUserDto dto){
       return ResponseEntity.ok(service.update(dto,dto.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("/{page}/{size}")
    public ResponseEntity<?> read(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return ResponseEntity.ok(service.read(PageRequest.of(page,size)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable("id") String id){
        return ResponseEntity.ok(service.read(id));
    }


    @GetMapping("/not-friends/{id}/{page}/{size}")
    public ResponseEntity<?> readByNotFriends(@PathVariable("id") String id,@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return ResponseEntity.ok(service.readByNotFriends(PageRequest.of(page, size), id));
    }
    @GetMapping("/pages/{size}")
    public ResponseEntity<?> getPages(@PathVariable("size") Integer size){
        return ResponseEntity.ok(service.pages(size));
    }


}
