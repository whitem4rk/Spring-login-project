package Spring.domain.chat.entity;

import Spring.domain.login.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "room")
    private List<RoomUnreadUser> roomUnreadUsers = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<RoomUser> roomUsers = new ArrayList<>();

    public Room(User user) {
        this.user = user;
    }

}
