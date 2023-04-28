package Spring.domain.login.service;

import Spring.domain.login.dto.RegisterRequest;
import Spring.domain.login.entity.user.User;
import Spring.domain.login.repository.UserRepository;
import Spring.global.error.EntityAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static Spring.global.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Transactional
    public boolean signup(RegisterRequest registerRequest) {
        if (repository.existsByUsername(registerRequest.getUsername())) {
            throw new EntityAlreadyExistException(USERNAME_ALREADY_EXIST);
        }
        if (repository.existsByUserid(registerRequest.getUserid())) {
            throw new EntityAlreadyExistException(USERID_ALREADY_EXIST);
        }

        final String username = registerRequest.getUsername();
        final User user = convertRegisterRequestToUser(registerRequest);
        repository.save(user);

        return true;
    }

    @Override
    public User findUser(String userid) {
        User user = repository.findByUserid(userid);
        return user;
    }

    @Override
    public User findUser(String userid, String password) {
        User user = repository.findByUseridAndPassword(userid, password);
        return user;
    }

    @Override
    public String findPassword(String userid) {
        User user = repository.findByUserid(userid);
        return user.getPassword();
    }

    @Override
    @Transactional
    public void changePassword(String userid, String password, String newPassword) {
        User user = repository.findByUseridAndPassword(userid, password);
        user.updatePassword(newPassword);
    }

    @Override
    @Transactional
    public void deleteUser(String userid, String password) {
        repository.deleteByUseridAndPassword(userid, password);
    }

    @Override
    @Transactional
    public void logout(String userid) {
        return;
    }

    private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .userid(registerRequest.getUserid())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .build();
    }

}
