package Spring.domain.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Length(min = 3, max = 30, message = "사용자 이름은 3문자 이상 30문자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]+$", message = "사용자 이름은 한글, 영어 대소문자, 숫자로만 이루어져야 합니다.")
    private String username;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 6, max = 30, message = "아이디은 6문자 이상 30문자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "아이디는 영어 대소문자, 숫자로만 이루어져야 합니다.")
    private String userid;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(max = 20, message = "비밀번호는 20문자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8자 이상이어야 하며 최소 하나의 대소문자, 숫자, 특수문자가 필요.합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "이메일 인증코드를 입력해주세요.")
    @Length(max = 6, min = 6, message = "인증코드는 6자리 입니다.")
    private String code;

}
