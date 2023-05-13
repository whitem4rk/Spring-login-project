package Spring.global.security.handler;

import Spring.global.error.ErrorCode;
import Spring.global.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(ErrorCode.ACCOUNT_MISMATCH));
            os.flush();
        }
    }
}
