package Spring.global.util;

import Spring.domain.login.dto.JwtDto;
import Spring.domain.login.entity.user.User;
import Spring.domain.login.exception.JwtExpiredException;
import Spring.domain.login.exception.JwtInvalidException;
import Spring.global.error.BusinessException;
import Spring.global.security.token.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final static String CLAIM_AUTHORITIES_KEY = "authorities";
    private final static String CLAIM_MEMBER_ID_KEY = "userid";
    private final static String ACCESS_TOKEN_SUBJECT = "accessToken";
    private final static String REFRESH_TOKEN_SUBJECT = "refreshToken";
    private final Key JWT_KEY;
    @Value("${access-token-expires}")
    private long ACCESS_TOKEN_EXPIRES;

    @Value("${refresh-token-expires}")
    private long REFRESH_TOKEN_EXPIRES;

    public JwtUtil(@Value("${jwt.key}") byte[] key) {
        this.JWT_KEY = Keys.hmacShaKeyFor(key);
    }

    public String regenerateAccessJwt(Authentication authentication) {
        final String authoritiesString = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final long currentTime = (new Date()).getTime();
        final Date accessTokenExpiresIn = new Date(currentTime + ACCESS_TOKEN_EXPIRES);

        final String accessToken = Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(CLAIM_MEMBER_ID_KEY, authentication.getName())
                .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
                .setExpiration(accessTokenExpiresIn)
                .signWith(JWT_KEY, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    public JwtDto generateJwtDto(Authentication authentication) {
        final String authoritiesString = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final long currentTime = (new Date()).getTime();

        final Date accessTokenExpiresIn = new Date(currentTime + ACCESS_TOKEN_EXPIRES);
        final Date refreshTokenExpiresIn = new Date(currentTime + REFRESH_TOKEN_EXPIRES);

        final String accessToken = Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(CLAIM_MEMBER_ID_KEY, authentication.getName())
                .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
                .setExpiration(accessTokenExpiresIn)
                .signWith(JWT_KEY, SignatureAlgorithm.HS512)
                .compact();

        final String refreshToken = Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .claim(CLAIM_MEMBER_ID_KEY, authentication.getName())
                .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(JWT_KEY, SignatureAlgorithm.HS512)
                .compact();

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtDto generateJwtDto(User user) {
        final String authoritiesString = user.getGrade().toString();
        long currentTime = (new Date()).getTime();

        final Date accessTokenExpiresIn = new Date(currentTime + ACCESS_TOKEN_EXPIRES);
        final Date refreshTokenExpiresIn = new Date(currentTime + REFRESH_TOKEN_EXPIRES);

        final String accessToken = Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(CLAIM_MEMBER_ID_KEY, user.getUserid().toString())
                .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
                .setExpiration(accessTokenExpiresIn)
                .signWith(JWT_KEY, SignatureAlgorithm.HS512)
                .compact();

        final String refreshToken = Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .claim(CLAIM_MEMBER_ID_KEY, user.getUserid().toString())
                .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(JWT_KEY, SignatureAlgorithm.HS512)
                .compact();

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) throws BusinessException {
        Claims claims = parseClaims(token);
        final List<SimpleGrantedAuthority> authorities = Arrays.stream(
                        claims.get(CLAIM_AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        final org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User((String) claims.get(CLAIM_MEMBER_ID_KEY), "", authorities);

        return JwtAuthenticationToken.of(principal, token, authorities);
    }


    private Claims parseClaims(String token) throws BusinessException {
        try {
            return Jwts.parserBuilder().setSigningKey(JWT_KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (Exception e) {
            throw new JwtInvalidException();
        }
    }

    public boolean expirationCheck(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(JWT_KEY).build().parseClaimsJws(token).getBody();
            return true;
        }catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Cookie extractJwt(Cookie[] cookies, String cookieName) {
        final Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);

        return cookie;
    }

}
