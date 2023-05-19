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
    public void addRefreshToken(Long memberId, String tokenValue) {
        final RefreshToken refreshToken = RefreshToken.builder()
                .memberId(memberId)
                .value(tokenValue)
                .build();
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findRefreshToken(Long memberId, String value) {
        return refreshTokenRedisRepository.findByMemberIdAndValue(memberId, value);
    }

    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByValue(Long memberId, String value) {
        final RefreshToken refreshToken = refreshTokenRedisRepository.findByMemberIdAndValue(memberId, value)
                .orElseThrow(JwtInvalidException::new);
        refreshTokenRedisRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByMemberIdAndId(Long memberId, String id) {
        final RefreshToken refreshToken = refreshTokenRedisRepository.findByMemberIdAndId(memberId, id)
                .orElseThrow(JwtInvalidException::new);
        refreshTokenRedisRepository.delete(refreshToken);
    }

}
