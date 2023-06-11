package Spring.global.security.filter;

import Spring.global.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private RequestMatcher matcher;
    private final static String ACCESS_TOKEN_SUBJECT = "accessToken";
    private final static String REFRESH_TOKEN_SUBJECT = "refreshToken";

    public JwtAuthenticationFilter(RequestMatcher matcher, JwtUtil jwtUtil) {
        this.matcher = matcher;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isAllowedPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final Cookie[] cookies = request.getCookies();
        final Cookie accessJwt = jwtUtil.extractJwt(cookies, ACCESS_TOKEN_SUBJECT);
        final Cookie refreshJwt = jwtUtil.extractJwt(cookies, REFRESH_TOKEN_SUBJECT);


        if (jwtUtil.expirationCheck(accessJwt.getValue())) {
            Authentication authentication = jwtUtil.getAuthentication(accessJwt.getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.addCookie(new Cookie(ACCESS_TOKEN_SUBJECT, accessJwt.getValue()));
        } else if (jwtUtil.expirationCheck(refreshJwt.getValue())) {
            Authentication authentication = jwtUtil.getAuthentication(refreshJwt.getValue());
            final String newAccessJwt = jwtUtil.regenerateAccessJwt(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.addCookie(new Cookie(ACCESS_TOKEN_SUBJECT, newAccessJwt));
        } else {
            response.sendRedirect("/login");
            return;
        }

        super.doFilter(request, response, filterChain);
    }

    private boolean isAllowedPath(HttpServletRequest request) {
        return !this.matcher.matches(request);
    }
}
