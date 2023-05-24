package Spring.domain.login.repository;

import Spring.domain.login.entity.redis.RegisterCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisterCodeRedisRepository extends CrudRepository<RegisterCode, String> {

    Optional<RegisterCode> findByUserid(String userid);
}
