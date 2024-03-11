package com.kstoi.social.config.auth;

import com.kstoi.social.services.SUserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private SUserService service;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails user = service.loadUserByUsername(username);

        try{
            if(MessageDigest.getInstance("sha512").isEqual(
                    user.getPassword().getBytes(StandardCharsets.UTF_8),
                    authentication.getCredentials().toString().getBytes(StandardCharsets.UTF_8)
            )){
                UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(username,authentication.getCredentials().toString());
                return auth_token;
            }
        }catch(Exception e){
            throw e;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
