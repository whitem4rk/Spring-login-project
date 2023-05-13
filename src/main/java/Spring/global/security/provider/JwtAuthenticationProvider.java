package Spring.global.security.provider;

import Spring.global.security.token.JwtAuthenticationToken;
import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String jwt = (String) authentication.getPrincipal();
        return jwtUtil.getAuthentication(jwt);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
