package projeto.api.blog;

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
import org.springframework.security.authentication.AuthenticationManager;

import projeto.api.blog.model.Role;
import projeto.api.blog.model.User;
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
    private AuthenticationManager authenticationManager;

    private UserService userService;

    private Role role;

    @BeforeEach
    private void setup() {
        userService = new UserService(userRepository,roleRepository,authenticationManager);
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
}
