package Spring.login.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
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

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    public void changePassword(String password) {
        this.password = password;
    }

}

