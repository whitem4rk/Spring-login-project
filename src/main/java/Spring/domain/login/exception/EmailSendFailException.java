package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class EmailSendFailException extends BusinessException {

    public EmailSendFailException() {
        super(ErrorCode.EMAIL_SEND_FAIL);
    }
}
