package Spring.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    REGISTER_SUCCESS(200, "M001", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "M002", "로그인에 성공하였습니다."),
    REISSUE_SUCCESS(200, "M003", "재발급에 성공하였습니다."),
    UPDATE_PASSWORD_SUCCESS(200, "M010", "회원 비밀번호를 변경하였습니다."),
    CHECK_USERNAME_GOOD(200, "M011", "사용가능한 username 입니다."),
    CHECK_USERNAME_BAD(200, "M012", "사용불가능한 username 입니다."),
    CONFIRM_EMAIL_FAIL(200, "M013", "이메일 인증을 완료할 수 없습니다."),
    SEND_CONFIRM_EMAIL_SUCCESS(200, "M014", "인증코드 이메일을 전송하였습니다."), // 결번 M015
    SEND_RESET_PASSWORD_EMAIL_SUCCESS(200, "M017", "비밀번호 재설정 메일을 전송했습니다."),
    RESET_PASSWORD_SUCCESS(200, "M018", "비밀번호 재설정에 성공했습니다."),
    LOGIN_WITH_CODE_SUCCESS(200, "M019", "비밀번호 재설정 코드로 로그인 했습니다."),
    LOGOUT_SUCCESS(200, "M020", "로그아웃하였습니다.");

    private final int status;
    private final String code;
    private final String message;
}
