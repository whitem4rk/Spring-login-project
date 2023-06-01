package Spring.domain.chat.dto;

import Spring.domain.login.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSimpleInfo {

    private String username;

    public UserSimpleInfo(User user) {
        this.username = user.getUsername();
    }

}
