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
    public void signup(User user) {

    }

    @Override
    public void findMember(String userid) {

    }

    @Override
    public String findPassword(String userid) {
        return null;
    }

    @Override
    public void changePassword(String userid, String password) {

    }

    @Override
    public void deleteUser(String userid) {

    }

    @Override
    public void logout(String userid) {

    }
}
