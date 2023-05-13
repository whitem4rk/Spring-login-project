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

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Value("${refresh-token-expires}")
    private long REFRESH_TOKEN_EXPIRES;

    public JwtUtil(@Value("${jwt.key}") byte[] key) {
        this.JWT_KEY = Keys.hmacShaKeyFor(key);
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
                .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
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
                .type(BEARER_TYPE)
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
                .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
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
                .type(BEARER_TYPE)
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
        final org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User((String) claims.get(CLAIM_MEMBER_ID_KEY), "", authorities);

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

    public String extractJwt(String authenticationHeader) {
        if (authenticationHeader == null) {
            throw new JwtInvalidException();
        } else if (!authenticationHeader.startsWith(BEARER_TYPE_PREFIX)) {
            throw new JwtInvalidException();
        }
        return authenticationHeader.substring(JWT_PREFIX_LENGTH);
    }



}
