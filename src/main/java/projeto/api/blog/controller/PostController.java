package projeto.api.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import projeto.api.blog.model.Post;
import projeto.api.blog.model.DTO.UserDTO;
import projeto.api.blog.responses.DefaultResponse;
import projeto.api.blog.service.PostService;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<DefaultResponse> createPost(@AuthenticationPrincipal UserDTO userDTO, @Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userDTO, post));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Post>> findAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<DefaultResponse> updatePost(@AuthenticationPrincipal UserDTO userDTO, @Valid @RequestBody Post post, @PathVariable long postId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.updatePost(post, userDTO, postId));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<DefaultResponse> deletePost(@AuthenticationPrincipal UserDTO userDTO, @PathVariable long postId) {
        return ResponseEntity.ok(postService.deletePost(userDTO, postId));
    }
}
