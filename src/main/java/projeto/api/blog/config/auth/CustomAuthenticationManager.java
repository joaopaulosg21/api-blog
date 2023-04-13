package projeto.api.blog.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import projeto.api.blog.model.User;
import projeto.api.blog.model.DTO.LoginDTO;
import projeto.api.blog.repository.UserRepository;


public class CustomAuthenticationManager implements AuthenticationManager{

    @Autowired
    private UserRepository userRepository;
    

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginDTO login = (LoginDTO) authentication.getCredentials();
        Optional<User> optionalUser = userRepository.findByEmail(login.getEmail());

        boolean b = new BCryptPasswordEncoder().matches(login.getPassword(), optionalUser.get().getPassword());
        
        if(!b) {
            throw new BadCredentialsException("Wrong Email or password");
        }
        
        return authentication;
    }
    
}
