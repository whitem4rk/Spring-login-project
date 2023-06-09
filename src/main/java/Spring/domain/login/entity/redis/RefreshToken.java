package Spring.domain.login.entity.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@RedisHash("refresh_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken implements Serializable {

    @Id
    @Indexed
    private String pk;

    @Indexed
    private String value;

    @Indexed
    private Long id;

    @TimeToLive(unit = TimeUnit.DAYS)
    private Long timeout = 7L;

    private LocalDateTime lastUpdateDate;


    @Builder
    public RefreshToken(Long id, String value) {
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.id = id;
        this.value = value;
    }

    public void updateToken(String newValue) {
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.value = newValue;
    }
}
