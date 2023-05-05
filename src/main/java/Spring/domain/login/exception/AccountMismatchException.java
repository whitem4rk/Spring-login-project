package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class AccountMismatchException extends BusinessException {

    public AccountMismatchException() {
        super(ErrorCode.ACCOUNT_MISMATCH);
    }
}
