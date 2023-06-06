package Spring.global.security.filter;

import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.Access;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter2 extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private List<String> allowedPaths;
    private final static String ACCESS_TOKEN_SUBJECT = "accessToken";
    private final static String REFRESH_TOKEN_SUBJECT = "refreshToken";

    @Autowired
    public JwtAuthenticationFilter2(List<String> allowedPaths, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.allowedPaths = allowedPaths;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestURI = request.getRequestURI();

        if (!isAllowedPath(requestURI)) {
            final Cookie[] cookies = request.getCookies();
            final String accessJwt = jwtUtil.extractJwt(cookies, ACCESS_TOKEN_SUBJECT);

            if (jwtUtil.expirationCheck(accessJwt)) {
                Authentication authentication = jwtUtil.getAuthentication(accessJwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                String refreshJwt = jwtUtil.extractJwt(cookies, REFRESH_TOKEN_SUBJECT);

                if (jwtUtil.expirationCheck(refreshJwt)) {
                    Authentication authentication = jwtUtil.getAuthentication(refreshJwt);
                    final String newAccessJwt = jwtUtil.regenerateAccessJwt(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    response.addCookie(new Cookie(ACCESS_TOKEN_SUBJECT, newAccessJwt));
                } else {
                    response.sendRedirect("/login");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAllowedPath(String path) {
        return allowedPaths.stream().anyMatch(path::startsWith);
    }
}
