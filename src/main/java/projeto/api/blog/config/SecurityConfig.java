package projeto.api.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import projeto.api.blog.config.auth.CustomAuthenticationManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((request) -> {
            request.antMatchers(HttpMethod.POST, "/user/**").permitAll();
        });

        http.cors().disable();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return new CustomAuthenticationManager();
    }

}
