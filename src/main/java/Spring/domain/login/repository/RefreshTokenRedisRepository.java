package Spring.domain.login.repository;

import Spring.domain.login.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    List<RefreshToken> findAllByMemberId(Long memberId);

    Optional<RefreshToken> findByMemberIdAndValue(Long memberId, String value);

    Optional<RefreshToken> findByMemberIdAndId(Long MemberId, String id);
}
