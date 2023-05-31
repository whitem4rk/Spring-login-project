package Spring.domain.login.dto;

import Spring.domain.login.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private boolean hasStory;

    @QueryProjection
    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hasStory = false;
    }

}
