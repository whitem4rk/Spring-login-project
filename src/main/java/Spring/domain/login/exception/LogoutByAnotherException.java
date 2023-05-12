package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class LogoutByAnotherException extends BusinessException {

    public LogoutByAnotherException() {
        super(ErrorCode.LOGOUT_BY_ANOTHER);
    }
}
