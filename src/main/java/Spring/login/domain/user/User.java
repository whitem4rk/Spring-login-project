package Spring.login.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    @NotBlank(message = "유저명은 빈칸이거나 Null일 수 없습니다.")
    private String username;

    @Column(length = 30, nullable = false, unique = true)
    @NotBlank(message = "아이디는 빈칸이거나 Null일 수 없습니다.")
    private String userid;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "패스워드는 빈칸이거나 Null일 수 없습니다.")
    private String password;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUsername(String username) {
        this.username = username;
    }
}

