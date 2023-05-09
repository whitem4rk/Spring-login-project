package Spring.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtResponse {

    private String type;

    private String accessToken;
}
