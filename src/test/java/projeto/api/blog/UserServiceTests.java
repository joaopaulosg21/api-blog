package projeto.api.blog;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import projeto.api.blog.config.auth.CustomAuthenticationManager;
import projeto.api.blog.config.auth.TokenService;
import projeto.api.blog.model.Role;
import projeto.api.blog.model.User;
import projeto.api.blog.model.DTO.LoginDTO;
import projeto.api.blog.repository.RoleRepository;
import projeto.api.blog.repository.UserRepository;
import projeto.api.blog.responses.DefaultResponse;
import projeto.api.blog.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CustomAuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    private UserService userService;

    private Role role;

    @BeforeEach
    private void setup() {
        userService = new UserService(userRepository,roleRepository,authenticationManager,tokenService);
        role = new Role(1L,"cliente");
    }

    @Test
    public void createUserTest() {
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),role);
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        DefaultResponse response = userService.createUser(user);

        Assertions.assertEquals("Ok",response.getStatus());
        Assertions.assertEquals("User created", response.getMessage());
        
    }

    @Test
    public void createUserExceptionTest() {
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),role);
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(user));

        Assertions.assertEquals("User with this email already registered", exception.getMessage());
    }

    @Test
    public void loginTest() {
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),role);
        LoginDTO loginDTO = new LoginDTO("teste@email.com", "123");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user, loginDTO, user.getAuthorities());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePassword);
        when(tokenService.generateLoginToken(any(Authentication.class))).thenReturn("Token");

        DefaultResponse response = userService.login(loginDTO);

        Assertions.assertEquals("Login Ok", response.getStatus());
        Assertions.assertEquals("Token",response.getMessage());
    }

    @Test
    public void loginTestExceptionEmailNotRegistered() {
        LoginDTO loginDTO = new LoginDTO("teste@email.com", "123");
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login(loginDTO));

        Assertions.assertEquals("Wrong Email or password",exception.getMessage());
    }
}
