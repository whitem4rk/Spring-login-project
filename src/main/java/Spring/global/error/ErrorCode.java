package Spring.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member
    MEMBER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),
    SIGHUP_FAIL(400, "M010", "회원 가입에 실패하였습니다."),
    USERNAME_ALREADY_EXIST(400, "M002", "이미 존재하는 사용자 이름입니다."),
    USERID_ALREADY_EXIST(400, "M003", "이미 존재하는 사용자 이름입니다."),
    AUTHENTICATION_FAIL(401, "M004", "로그인이 필요한 화면입니다."),
    AUTHORITY_INVALID(403, "M005", "권한이 없습니다."),
    ACCOUNT_MISMATCH(401, "M006", "계정 정보가 일치하지 않습니다."),
    EMAIL_NOT_CONFIRMED(400, "M007", "인증 이메일 전송을 먼저 해야합니다."),
    PASSWORD_RESET_FAIL(400, "M008", "잘못되거나 만료된 코드입니다."),
    PASSWORD_EQUAL_WITH_OLD(400, "M009", "기존 비밀번호와 동일하게 변경할 수 없습니다."),
    JWT_INVALID(401, "J001", "유효하지 않은 토큰입니다."),
    JWT_EXPIRED(401, "J002", "만료된 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(401, "J003", "만료된 REFRESH 토큰입니다. 재로그인 해주십시오."),
    FILTER_MUST_RESPOND(500, "G010", "필터에서 처리해야 할 요청이 Controller에 접근하였습니다."),
    LOGOUT_BY_ANOTHER(401,"M014", "다른 기기에 의해 로그아웃 되었습니다."),
    EMAIL_SEND_FAIL(500, "E001", "이메일 전송 중 오류가 발생했습니다.");
    private final int status;
    private final String code;
    private final String message;

}
