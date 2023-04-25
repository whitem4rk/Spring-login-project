package Spring.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member
    MEMBER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),
    USERNAME_ALREADY_EXIST(400, "M002", "이미 존재하는 사용자 이름입니다."),
    USERID_ALREADY_EXIST(400, "M003", "이미 존재하는 사용자 이름입니다."),
    AUTHENTICATION_FAIL(401, "M004", "로그인이 필요한 화면입니다."),
    AUTHORITY_INVALID(403, "M005", "권한이 없습니다."),
    ACCOUNT_MISMATCH(401, "M006", "계정 정보가 일치하지 않습니다."),
    EMAIL_NOT_CONFIRMED(400, "M007", "인증 이메일 전송을 먼저 해야합니다."),
    PASSWORD_RESET_FAIL(400, "M008", "잘못되거나 만료된 코드입니다."),
    PASSWORD_EQUAL_WITH_OLD(400, "M009", "기존 비밀번호와 동일하게 변경할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;

}
