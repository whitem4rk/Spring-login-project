package Spring.global.util;

import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private final static String CLAIM_AUTHORITIES_KEY = "authorities";
    private final static String CLAIM_JWT_TYPE_KEY = "type";
    private final static String CLAIM_MEMBER_ID_KEY = "userid";
    private final static String BEARER_TYPE_PREFIX = "Bearer ";
    private final static String BEARER_TYPE = "Bearer";
    private final static String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private final static String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final int JWT_PREFIX_LENGTH = 7;
    private final Key JWT_KEY;
    @Value("${access-token-expires}")
    private long ACCESS_TOKEN_EXPIRES;
    @Value("${refresh-token-expires")
    private long REFRESH_TOKEN_EXPIRES;

    public JwtUtil(@Value("${jwt.key}") byte[] key) {
        this.JWT_KEY = Keys.hmacShaKeyFor(key);
    }
}
