package projeto.api.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.api.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByPublished(boolean status);
}
