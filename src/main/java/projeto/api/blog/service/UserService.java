package projeto.api.blog.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import projeto.api.blog.config.auth.TokenService;
import projeto.api.blog.model.Role;
import projeto.api.blog.model.User;
import projeto.api.blog.model.DTO.LoginDTO;
import projeto.api.blog.repository.RoleRepository;
import projeto.api.blog.repository.UserRepository;
import projeto.api.blog.responses.DefaultResponse;

@Service
@AllArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public DefaultResponse createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isEmpty()) {
            try {
                Role role = roleRepository.findByName("cliente");
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setRole(role);
                userRepository.save(user);
                return new DefaultResponse("Ok", "User created");
            }catch(Exception exc) {
                exc.printStackTrace();
            }
        }

        throw new RuntimeException("User with this email already registered");
    }

    public DefaultResponse login(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Wrong Email or password");
        }

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(optionalUser.get(), loginDTO, optionalUser.get().getAuthorities());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateLoginToken(authentication);

        return new DefaultResponse("Login Ok", token);
    }
}
