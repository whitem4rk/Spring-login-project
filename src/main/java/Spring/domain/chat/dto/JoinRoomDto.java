package Spring.domain.chat.dto;

import Spring.domain.login.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JoinRoomDto {

    private Long roomId;
    private MessageDto lastMessage;
    private boolean unreadFlag;
    private UserSimpleInfo inviter;
    private List<UserSimpleInfo> members = new ArrayList<>();

    @QueryProjection
    public JoinRoomDto(Long roomId, boolean unreadFlag, User user) {
        this.roomId = roomId;
        this.unreadFlag = unreadFlag;
        this.inviter = new UserSimpleInfo(user);
    }

}

