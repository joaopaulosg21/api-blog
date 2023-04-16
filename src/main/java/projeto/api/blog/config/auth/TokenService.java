package projeto.api.blog.config.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projeto.api.blog.model.DTO.UserDTO;

@Service
public class TokenService {
    
    @Value("${jwt.exp}")
    private String exp;

    @Value("${jwt.secret}")
    private String secret;
    
    public String generateLoginToken(Authentication authentication) {
        UserDTO user = (UserDTO) authentication.getPrincipal();
        
        long expiration = new Date().getTime() + Long.valueOf(exp);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .setSubject(Long.toString(user.getId()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception exc) {
            return false;
        }
    }

    public long getUserIdFromToken(String token) {
        long id = Long.valueOf(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());

        return id;
    }
}
