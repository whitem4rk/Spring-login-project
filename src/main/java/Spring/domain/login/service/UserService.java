package Spring.domain.login.service;

import Spring.domain.login.dto.RegisterRequest;
import Spring.domain.login.dto.UpdatePasswordRequest;
import Spring.domain.login.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean signup(RegisterRequest registerRequest);
    public void sendEmailConfirmation(String userid, String email);
    User findUser(String userid);
    User findUser(String userid, String password);
    String findPassword(String userid);
    void changePassword(UpdatePasswordRequest updatePasswordRequest);

    void deleteUser(String userid, String password);

    void logout(String refreshToken);

}
