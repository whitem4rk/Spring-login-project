package Spring;

import Spring.domain.login.repository.UserRepository;
import Spring.domain.login.service.UserService;
import Spring.domain.login.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository);
    }

//    @Bean
//    public Repository repository() {
//        return new
//    }

}
