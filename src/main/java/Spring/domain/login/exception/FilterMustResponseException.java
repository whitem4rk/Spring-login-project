package Spring.domain.login.exception;


import Spring.global.error.BusinessException;
import Spring.global.error.ErrorCode;

public class FilterMustResponseException extends BusinessException {

    public FilterMustResponseException() {
        super(ErrorCode.FILTER_MUST_RESPOND);
    }
}
