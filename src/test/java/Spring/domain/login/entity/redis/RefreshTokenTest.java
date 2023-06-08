package Spring.domain.login.entity.redis;

import Spring.domain.login.repository.RefreshTokenRedisRepository;
import Spring.global.config.EmbeddedRedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

@Import(EmbeddedRedisConfig.class)
@DataRedisTest
class RefreshTokenTest {

    @Autowired
    private RefreshTokenRedisRepository refreshTokenRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        refreshTokenRedisRepository.deleteAll();
    }

    @Test
    public void enroll() {
        //given
        RefreshToken refreshToken = RefreshToken.builder()
                .id(100L)
                .value("100")
                .build();
        //when
        refreshTokenRedisRepository.save(refreshToken);

        //then
        RefreshToken refreshToken1 = refreshTokenRedisRepository.findByIdAndValue(100L, "100").get();
        Assertions.assertThat(refreshToken1.getId()).isEqualTo(100L);
        Assertions.assertThat(refreshToken1.getValue()).isEqualTo("100");
    }

}