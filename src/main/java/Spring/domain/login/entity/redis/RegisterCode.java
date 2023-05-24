package Spring.domain.login.entity.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash
public class RegisterCode implements Serializable {

    @Id
    @Indexed
    private String userid;

    private String email;

    private String code;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long timeout = 300L;

    @Builder
    public RegisterCode(String userid, String code, String email) {
        this.userid = userid;
        this.code = code;
        this.email = email;
    }
}
