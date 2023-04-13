package projeto.api.blog.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import projeto.api.blog.model.User;
import projeto.api.blog.repository.UserRepository;

@AllArgsConstructor
@Service
public class AuthService implements UserDetailsService{

    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if(optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }

        throw new RuntimeException("User not found");
    }
    
}
