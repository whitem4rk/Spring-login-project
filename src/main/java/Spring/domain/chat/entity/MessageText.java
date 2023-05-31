package Spring.domain.chat.entity;

import Spring.domain.login.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TEXT")
@Table(name = "message_texts")
public class MessageText extends Message{

    @Lob
    @Column(name = "message_text_content")
    private String content;

    public MessageText(String content, User user, Room room) {
        super(user, room);
        this.content = content;
    }
}
