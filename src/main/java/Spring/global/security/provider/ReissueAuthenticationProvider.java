package Spring.global.security.provider;

import Spring.domain.login.entity.redis.RefreshToken;
import Spring.domain.login.exception.LogoutByAnotherException;
import Spring.domain.login.service.RefreshTokenService;
import Spring.global.security.token.ReissueAuthenticationToken;
import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReissueAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String refreshTokenString = (String) authentication.getPrincipal();
        final Authentication authenticated = jwtUtil.getAuthentication(refreshTokenString);
        final String memberId = (String) authenticated.getName();
        final RefreshToken refreshToken = refreshTokenService.findRefreshToken(Long.valueOf(memberId),
                refreshTokenString).orElseThrow(LogoutByAnotherException::new);
        this.deleteRefreshToken(refreshToken);
        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ReissueAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenService.deleteRefreshToken(refreshToken);
    }
}
