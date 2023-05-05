package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class PasswordEqualWithOldException extends BusinessException {
    public PasswordEqualWithOldException() {
        super(ErrorCode.PASSWORD_EQUAL_WITH_OLD);
    }
}
