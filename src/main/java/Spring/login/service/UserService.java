package Spring.login.service;

import Spring.login.domain.user.User;

public interface UserService {
    void signup(User user);

    User findUser(String userid);

    String findPassword(String userid);

    void changePassword(String userid, String password, String newPassword);

    void deleteUser(String userid, String password);

    void logout(String userid);
}
