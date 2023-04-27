package Spring.domain.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "이전비밀번호를 입력해주세요")
    @Length(max = 30, message = "비밀번호는 30문자 이하여야 합니다")
    private String oldPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8자 이상이어야 하며 최소 하나의 대소문자, 숫자, 특수문자가 필요.합니다.")
    @Length(max = 30, message = "비밀번호는 30문자 이하여야 합니다")
    private String newPassword;

}
