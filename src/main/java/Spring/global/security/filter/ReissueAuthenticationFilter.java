package Spring.global.security.filter;

import Spring.domain.login.exception.JwtInvalidException;
import Spring.global.security.token.ReissueAuthenticationToken;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

public class ReissueAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/reissue", "POST");

    public ReissueAuthenticationFilter() {
        super(ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            if (request.getCookies() == null) {
                throw new JwtInvalidException();
            }
            final Cookie refreshToken = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("refreshToken"))
                    .findFirst()
                    .orElseThrow(JwtInvalidException::new);
            final ReissueAuthenticationToken authRequest = ReissueAuthenticationToken.of(refreshToken.getValue());
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
}
