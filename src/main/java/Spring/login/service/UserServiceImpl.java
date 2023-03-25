package Spring.login.service;

import Spring.login.domain.user.User;
import Spring.login.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserid(String userid) {
        return userRepository.findByUserid(userid);
    }
}
