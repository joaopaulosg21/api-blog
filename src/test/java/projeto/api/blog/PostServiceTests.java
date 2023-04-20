package projeto.api.blog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void findAllPostsTest() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setPublished(true);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        when(postRepository.findAllByPublished(true)).thenReturn(posts);

        List<Post> response = postService.findAllPosts();

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Test title",response.get(0).getTitle());
    }
    
    @Test
    public void updatePostTest() {
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),null);

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setPublished(true);
        post.setUser(user);

        Post updatePost = new Post();
        updatePost.setTitle("New title");
        updatePost.setContent("New content");

        when(postRepository.findByPublishedAndUserIdAndId(true, user.getId(), post.getId())).thenReturn(Optional.of(post));

        when(postRepository.save(any(Post.class))).thenReturn(post);

        DefaultResponse response = postService.updatePost(updatePost, user.toDTO(), 1L);

        Assertions.assertEquals("Ok", response.getStatus());
        Assertions.assertEquals("Post updated", response.getMessage());
    }
    
    @Test
    public void updatePostExceptionTest() {

        Post updatePost = new Post();
        updatePost.setTitle("New title");
        updatePost.setContent("New content");
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),null);

        when(postRepository.findByPublishedAndUserIdAndId(anyBoolean(), anyLong(), anyLong())).thenReturn(Optional.empty());

        RuntimeException response = Assertions.assertThrows(RuntimeException.class, () -> postService.updatePost(updatePost, user.toDTO(), 1L));
        
        Assertions.assertEquals("Post does not exist", response.getMessage());
    }

    @Test 
    public void deletePostTest() {
        
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),null);
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setPublished(true);
        post.setUser(user);

        when(postRepository.findByPublishedAndUserIdAndId(true, user.getId(), post.getId())).thenReturn(Optional.of(post));

        DefaultResponse response = postService.deletePost(user.toDTO(), 1L);

        Assertions.assertEquals("Ok", response.getStatus());
        Assertions.assertEquals("Post deleted", response.getMessage());
    }

    @Test
    public void deletePostExceptionTest() {

        Post updatePost = new Post();
        updatePost.setTitle("New title");
        updatePost.setContent("New content");
        User user = new User(1L,"Test name", "teste@email.com","123",LocalDate.of(1998, 04, 11),null);

        when(postRepository.findByPublishedAndUserIdAndId(anyBoolean(), anyLong(), anyLong())).thenReturn(Optional.empty());

        RuntimeException response = Assertions.assertThrows(RuntimeException.class, () -> postService.deletePost(user.toDTO(), 1L));
        
        Assertions.assertEquals("Post does not exist", response.getMessage());

    }
}
