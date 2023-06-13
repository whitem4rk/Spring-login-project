package Spring.domain.login.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SendConfirmationEmailRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 6, max = 30, message = "아이디은 6문자 이상 30문자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "아이디는 영어 대소문자, 숫자로만 이루어져야 합니다.")
    private String userid;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 맞지 않습니다.")
    private String email;

}
