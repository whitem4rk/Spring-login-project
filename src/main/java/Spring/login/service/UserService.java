package Spring.login.service;

import Spring.login.domain.user.User;

public interface UserService {
    void save(User user);

    User findByUserid(String userid);
}
