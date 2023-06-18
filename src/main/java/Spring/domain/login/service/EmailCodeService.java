package Spring.domain.login.service;

import Spring.domain.login.entity.redis.RegisterCode;
import Spring.domain.login.exception.EmailNotConfirmedException;
import Spring.domain.login.repository.RegisterCodeRedisRepository;
import Spring.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCodeService {

    private static final int REGISTER_CODE_LENGTH = 6;
    private static final int RESET_PASSWORD_CODE_LENGTH = 30;
    private static final String REGISTER_EMAIL_SUBJECT_POSTFIX = ", Welcome to JSH world.";
    private static final String RESET_PASSWORD_EMAIL_SUBJECT_POSTFIX = ", recover your account's password.";

    private final UserRepository userRepository;
    private final RegisterCodeRedisRepository registerCodeRedisRepository;
    private final EmailService emailService;
    private String confirmEmailUI;

    public void sendRegisterCode(String userid, String email) {
        final String code = createConfirmationCode(REGISTER_CODE_LENGTH);
        emailService.sendHtmlTextEmail(userid + REGISTER_EMAIL_SUBJECT_POSTFIX, code, email);

        final RegisterCode registerCode = RegisterCode.builder()
                .userid(userid)
                .email(email)
                .code(code)
                .build();
        registerCodeRedisRepository.save(registerCode);
    }

    public boolean checkRegisterCode(String userid, String email, String code) {
        final RegisterCode registerCode = registerCodeRedisRepository.findByUserid(userid)
                .orElseThrow(EmailNotConfirmedException::new);

        if (!registerCode.getCode().equals(code) || !registerCode.getEmail().equals(email)) {
            return false;
        }

        registerCodeRedisRepository.delete(registerCode);
        return true;
    }

    private String createConfirmationCode(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    private String getRegisterEmailText(String email, String code) {
        return String.format(confirmEmailUI, email, code, email);
    }

}
