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

import projeto.api.blog.model.Post;
import projeto.api.blog.model.User;
import projeto.api.blog.repository.PostRepository;
import projeto.api.blog.repository.UserRepository;
import projeto.api.blog.responses.DefaultResponse;
import projeto.api.blog.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @BeforeEach
    private void setup() {
        postService = new PostService(postRepository, userRepository);
    }

    @Test
    public void createPostTest() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test title");
        post.setContent("Test content");
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),null);
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        DefaultResponse response = postService.createPost(user.toDTO(), post);

        Assertions.assertEquals("Ok", response.getStatus());
        Assertions.assertEquals("Post created",response.getMessage());
    }
}
