package Spring.domain.chat.dto;

import Spring.domain.chat.entity.MessageText;
import Spring.domain.login.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long roomId;
    private Long messageId;
    private UserDto sender;
    private Object content;
    private String messageType;
    private LocalDateTime messageDate;
    private List<UserDto> likeMembers = new ArrayList<>();

    public MessageDto(MessageText message) {
        this.roomId = message.getRoom().getId();
        this.messageId = message.getId();
        this.sender = new UserDto(message.getUser());
        this.messageDate = message.getCreatedDate();
        this.messageType = message.getDtype();
        this.content = message.getContent();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessagePostDTO {

        private String status;
        private Long postId;
        private Integer postImageCount;
        private String content;
        private UserDto uploader;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageStoryDTO {

        private String status;
        private Long storyId;
        private UserDto uploader;

    }

}
