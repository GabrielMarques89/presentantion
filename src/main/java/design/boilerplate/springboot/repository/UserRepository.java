package design.boilerplate.springboot.repository;

import design.boilerplate.springboot.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByCpf(String cpf);

  boolean existsByUsername(String username);

}
