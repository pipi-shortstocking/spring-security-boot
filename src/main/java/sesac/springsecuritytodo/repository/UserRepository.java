package sesac.springsecuritytodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sesac.springsecuritytodo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);
}
