package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class JwtInvalidException extends BusinessException {

    public JwtInvalidException() {
        super(ErrorCode.JWT_INVALID);
    }
}
