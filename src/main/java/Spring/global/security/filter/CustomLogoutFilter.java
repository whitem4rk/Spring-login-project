package Spring.global.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class CustomLogoutFilter extends LogoutFilter {

    private static final AntPathRequestMatcher ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/logout", "POST");

    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
        setLogoutRequestMatcher(ANT_PATH_REQUEST_MATCHER);
    }

}
