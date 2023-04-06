package Spring.login.service;

import Spring.login.domain.user.User;
import Spring.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public void signup(User user) {
        repository.save(user);
    }

    @Override
    public User findUser(String userid) {
        User user = repository.findByUserid(userid);
        return user;
    }

    @Override
    public String findPassword(String userid) {
        User user = repository.findByUserid(userid);
        return user.getPassword();
    }

    @Override
    public void changePassword(String userid, String password, String newPassword) {
        User user = repository.findByUseridAndPassword(userid, password);
        user.changePassword(newPassword);
    }

    @Override
    public void deleteUser(String userid, String password) {
        repository.deleteByUseridAndPassword(userid, password);
    }

    @Override
    public void logout(String userid) {
        return;
    }
}
