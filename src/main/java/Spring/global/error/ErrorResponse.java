package Spring.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode code) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
