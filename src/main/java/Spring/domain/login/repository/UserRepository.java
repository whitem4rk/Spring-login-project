package Spring.domain.login.repository;

import Spring.domain.login.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(String userid);

    User findByUseridAndPassword(String userid, String password);

    boolean existsByUsername(String username);

    boolean existsByUserid(String userid);

    void deleteByUseridAndPassword(String userid, String password);
}
