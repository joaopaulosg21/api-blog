package projeto.api.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.api.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

    Role findByName(String name);
    
}
