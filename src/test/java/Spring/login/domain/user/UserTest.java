package Spring.login.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTest {
    @Autowired
    private TestEntityManager entityManager;
    // id username userid password grade
    @Test
    public void createWhenSubjectIsNullShouldThrowException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User(null, null, null, null, null))
                .withMessage("User null test passed");
    }

    @Test
    public void createWhenSubjectIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User(null, "", "","",Grade.CLIENT))
                .withMessage("User empty test passed");
    }

    @Test
    public void saveShouldPersistData() {
        User user = this.entityManager.persistFlushFind(new User(null, "testname", "testid", "testpw", Grade.CLIENT));

        assertThat(user.getUsername()).isEqualTo("testname");
        assertThat(user.getUserid()).isEqualTo("testid");
        assertThat(user.getPassword()).isEqualTo("testpw");
        assertThat(user.getGrade()).isEqualTo(Grade.CLIENT);
    }
}