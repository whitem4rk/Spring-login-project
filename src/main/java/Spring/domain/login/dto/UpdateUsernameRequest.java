package Spring.domain.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUsernameRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Length(min = 3, max = 30, message = "사용자 이름은 3문자 이상 30문자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]+$", message = "사용자 이름은 한글, 영어 대소문자, 숫자로만 이루어져야 합니다.")
    private String newUsername;

}
