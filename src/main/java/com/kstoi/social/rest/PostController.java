package com.kstoi.social.rest;

import com.kstoi.social.config.auth.AuthenticationManager;
import com.kstoi.social.config.auth.JwtService;
import com.kstoi.social.models.dtos.PostDto;
import com.kstoi.social.models.dtos.SUserDto;
import com.kstoi.social.services.PostService;
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
@RequestMapping("/post")
public class PostController {
    private PostService service;
    private SUserService userService;
    private AuthenticationManager authManager;
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody PostDto dto){
        Map<String, String> result = service.create(dto);
        return ResponseEntity.ok(result);
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody PostDto dto){
       return ResponseEntity.ok(service.update(dto,dto.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("/{user}/{page}/{size}")
    public ResponseEntity<?> read(@PathVariable("user") String user,@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        SUserDto userDto = userService.read(user);
        return ResponseEntity.ok(service.readByFriends(userDto.getFriends(),page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.read(id));
    }
    @GetMapping("pages/{size}")
    public ResponseEntity<?> getPages(@PathVariable("size") Integer size){
        return ResponseEntity.ok(service.pages(size));
    }



}
