package projeto.api.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import projeto.api.blog.model.User;
import projeto.api.blog.responses.DefaultResponse;
import projeto.api.blog.service.UserService;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<DefaultResponse> createNewUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }
}
