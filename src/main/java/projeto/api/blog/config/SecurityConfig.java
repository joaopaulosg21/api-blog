package projeto.api.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import projeto.api.blog.config.auth.AuthFilter;
import projeto.api.blog.config.auth.CustomAuthenticationManager;
import projeto.api.blog.config.auth.TokenService;
import projeto.api.blog.repository.UserRepository;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private TokenService tokenService;

    private UserRepository userRepository;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((request) -> {
            request.antMatchers(HttpMethod.POST,"/account/**").permitAll()
            .anyRequest().authenticated()
            .and().addFilterBefore(new AuthFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
        });
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return new CustomAuthenticationManager();
    }

}
