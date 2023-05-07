package Spring.global.util;

import Spring.domain.login.entity.user.User;
import Spring.domain.login.exception.JwtInvalidException;
import Spring.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public Long getLoginMemberIdOrNull() {
        try {
            final String id = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.valueOf(id);
        } catch (Exception e) {
            return -1L;
        }
    }

    public Long getLoginUserid() {
        try {
            final String id = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.valueOf(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public User getLoginUser() {
        try {
            final Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
            return userRepository.findById(id).orElseThrow(JwtInvalidException::new);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
