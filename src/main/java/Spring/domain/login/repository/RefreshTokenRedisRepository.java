package Spring.domain.login.repository;

import Spring.domain.login.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    List<RefreshToken> findAllById(Long Id);

    Optional<RefreshToken> findByIdAndValue(Long id, String value);

    Optional<RefreshToken> findByIdAndPk(Long id, String pk);
}
