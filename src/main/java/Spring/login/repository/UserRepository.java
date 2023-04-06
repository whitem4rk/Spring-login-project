package Spring.login.repository;

import Spring.login.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(String userid);

    User findByUseridAndPassword(String userid, String password);

    void deleteByUseridAndPassword(String userid, String password);
}
