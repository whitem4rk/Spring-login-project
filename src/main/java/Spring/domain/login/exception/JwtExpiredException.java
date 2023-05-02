package Spring.domain.login.exception;

import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class JwtExpiredException extends BusinessException {

    public JwtExpiredException() {
        super(ErrorCode.JWT_EXPIRED);
    }
}
