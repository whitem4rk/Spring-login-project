package Spring.domain.login.service;

import Spring.domain.login.dto.RegisterRequest;
import Spring.domain.login.dto.UpdatePasswordRequest;
import Spring.domain.login.entity.user.User;
import Spring.domain.login.exception.AccountMismatchException;
import Spring.domain.login.exception.PasswordEqualWithOldException;
import Spring.domain.login.repository.UserRepository;
import Spring.global.error.EntityAlreadyExistException;
import Spring.global.util.AuthUtil;
import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static Spring.global.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthUtil authUtil;
    private final JwtUtil jwtUtil;
    private final UserRepository repository;
    private final RefreshTokenService refreshTokenService;
    private final EmailCodeService emailCodeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean signup(RegisterRequest registerRequest) {
        if (repository.existsByUsername(registerRequest.getUsername())) {
            throw new EntityAlreadyExistException(USERNAME_ALREADY_EXIST);
        }
        if (repository.existsByUserid(registerRequest.getUserid())) {
            throw new EntityAlreadyExistException(USERID_ALREADY_EXIST);
        }

        final String username = registerRequest.getUsername();
        if (!emailCodeService.checkRegisterCode(username, registerRequest.getEmail(), registerRequest.getCode())) {
            return false;
        }


        final User user = convertRegisterRequestToUser(registerRequest);
        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        repository.save(user);

        return true;
    }

    @Override
    public void sendEmailConfirmation(String userid, String email) {
        if (repository.existsByUserid(userid)) {
            throw new EntityAlreadyExistException(USERID_ALREADY_EXIST);
        }
        emailCodeService.sendRegisterCode(userid, email);
    }

    @Override
    public User findUser(String userid) {
        Optional<User> user = repository.findByUserid(userid);
        return user.get();
    }

    @Override
    public User findUser(String userid, String password) {
        User user = repository.findByUseridAndPassword(userid, password);
        return user;
    }

    @Override
    public String findPassword(String userid) {
        Optional<User> user = repository.findByUserid(userid);
        return user.get().getPassword();
    }

    @Override
    @Transactional
    public void changePassword(UpdatePasswordRequest updatePasswordRequest) {
        final User user = authUtil.getLoginUser();
        if (!bCryptPasswordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new AccountMismatchException();
        }
        if (updatePasswordRequest.getOldPassword().equals(updatePasswordRequest.getNewPassword())) {
            throw new PasswordEqualWithOldException();
        }
        final String encryptedPassword = bCryptPasswordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.updatePassword(encryptedPassword);
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userid, String password) {
        repository.deleteByUseridAndPassword(userid, password);
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.deleteRefreshTokenByValue(authUtil.getLoginId(), refreshToken);
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
