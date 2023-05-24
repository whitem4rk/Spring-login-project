package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class EmailNotConfirmedException extends BusinessException {

    public EmailNotConfirmedException() {
        super(ErrorCode.EMAIL_NOT_CONFIRMED);
    }
}
