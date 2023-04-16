package projeto.api.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.api.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    
}
