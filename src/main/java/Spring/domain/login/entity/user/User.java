package Spring.domain.login.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(length = 30, nullable = false, unique = true)
    private String userid;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Builder
    public User(Long id, String username, String userid, String password, String email) {
        this.username = username;
        this.userid = userid;
        this.password = password;
        this.email = email;
        // 관리자 계정은 직접 관리
        this.grade = Grade.CLIENT;
    }

    public void updateUsername(String password) {
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}

