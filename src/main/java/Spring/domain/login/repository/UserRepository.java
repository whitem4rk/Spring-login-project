package Spring.domain.login.repository;

import Spring.domain.login.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);

    User findByUseridAndPassword(String userid, String password);

    boolean existsByUsername(String username);

    boolean existsByUserid(String userid);

    void deleteByUseridAndPassword(String userid, String password);

    List<User> findAllByUsernameIn(Collection<String> usernames);
}
