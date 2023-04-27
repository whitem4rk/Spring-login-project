package Spring.domain.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 6, max = 30, message = "아이디은 6문자 이상 30문자 이하여야 합니다.")
    private String userid;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(max = 30, message = "비밀번호는 30문자 이하여야 합니다.")
    private String password;

}