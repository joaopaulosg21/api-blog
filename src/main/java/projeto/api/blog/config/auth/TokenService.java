package projeto.api.blog.config.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import projeto.api.blog.model.User;

@Service
public class TokenService {
    
    @Value("${jwt.exp}")
    private String exp;

    @Value("${jwt.secret}")
    private String secret;
    
    public String generateLoginToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        
        long expiration = new Date().getTime() + Long.valueOf(exp);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .setSubject(user.getId().toString())
                .compact();
    }

}
