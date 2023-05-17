package Spring.global.security.handler;

import Spring.domain.login.dto.JwtDto;
import Spring.domain.login.service.RefreshTokenService;
import Spring.global.result.ResultCode;
import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final ResultCode DEFAULT_RESULT_CODE = ResultCode.LOGOUT_SUCCESS;
    private Map<String, ResultCode> resultCodeMap;
    @Value("${refresh-token-expires}")
    private int REFRESH_TOKEN_EXPIRES;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final JwtDto jwtDto = jwtUtil.generateJwtDto(authentication);
        refreshTokenService.addRefreshToken(Long.valueOf(authentication.getName()), jwtDto.getRefreshToken());
        addCookie(response, jwtDto.getRefreshToken());

        final ResultCode resultCode = getResultCode(request);
        response.setStatus(resultCode.getStatus());
        response.sendRedirect("/home");
    }

    public void setResultCodeMap(Map<String, ResultCode> resultCodeMap) {
        this.resultCodeMap = resultCodeMap;
    }

    protected ResultCode getResultCode(HttpServletRequest request) {
        if (resultCodeMap != null && resultCodeMap.containsKey(request.getRequestURI())) {
            return resultCodeMap.get(request.getRequestURI());
        } else {
            return DEFAULT_RESULT_CODE;
        }
    }

    protected void addCookie(HttpServletResponse response, String refreshTokenString) {
        final Cookie cookie = new Cookie("refreshToken", refreshTokenString);
        cookie.setMaxAge(REFRESH_TOKEN_EXPIRES);

        // cookie.setSecure(true); https 미지원
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
