package projeto.api.blog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public List<Post> findAllPosts() {
        return postRepository.findAllByPublished(true);
    }

    public DefaultResponse updatePost(Post post, UserDTO userDTO, long postId) {
        Optional<Post> optionalPost = postRepository.findByPublishedAndUserIdAndId(true, userDTO.getId(), postId);

        if(optionalPost.isEmpty()) {
            throw new RuntimeException("Post does not exist");
        }

        try {
            Post updatedPost = optionalPost.get();
            updatedPost.setTitle(post.getTitle());
            updatedPost.setContent(post.getContent());
            updatedPost.setEdit_date(LocalDate.now());
            postRepository.save(updatedPost);
            return new DefaultResponse("Ok", "Post updated");
        }catch(Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }
}
