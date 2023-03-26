package Spring.login.service;

import Spring.login.domain.user.User;

public interface UserService {
    void signup(User user);

    void findMember(String userid);

    String findPassword(String userid);

    void changePassword(String userid, String password);

    void deleteUser(String userid);

    void logout(String userid);
}
