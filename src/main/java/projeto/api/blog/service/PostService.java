package projeto.api.blog.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import projeto.api.blog.model.Post;
import projeto.api.blog.model.User;
import projeto.api.blog.model.DTO.UserDTO;
import projeto.api.blog.repository.PostRepository;
import projeto.api.blog.repository.UserRepository;
import projeto.api.blog.responses.DefaultResponse;

@Service
@AllArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public DefaultResponse createPost(UserDTO userDTO, Post post) {

        post.setCreation_date(LocalDate.now());
        post.setPublished(false);

        try {
            User user = userRepository.findByEmail(userDTO.getEmail()).get();
            post.setUser(user);
            postRepository.save(post);
            return new DefaultResponse("Ok","Post created");
        }catch(Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }

    }
}
