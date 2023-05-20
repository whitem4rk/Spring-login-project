package Spring.domain.login.service;

import Spring.domain.login.entity.redis.RefreshToken;
import Spring.domain.login.exception.JwtInvalidException;
import Spring.domain.login.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public void addRefreshToken(Long id, String tokenValue) {
        final RefreshToken refreshToken = RefreshToken.builder()
                .id(id)
                .value(tokenValue)
                .build();
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findRefreshToken(Long id, String value) {
        return refreshTokenRedisRepository.findByIdAndValue(id, value);
    }

    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByValue(Long id, String value) {
        final RefreshToken refreshToken = refreshTokenRedisRepository.findByIdAndValue(id, value)
                .orElseThrow(JwtInvalidException::new);
        refreshTokenRedisRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByIdAndPk(Long id, String pk) {
        final RefreshToken refreshToken = refreshTokenRedisRepository.findByIdAndPk(id, pk)
                .orElseThrow(JwtInvalidException::new);
        refreshTokenRedisRepository.delete(refreshToken);
    }

}
