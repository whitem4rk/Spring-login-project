package Spring.login.service;

import Spring.login.domain.user.Grade;
import Spring.login.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void signup() {
        // given
        User user = new User(null, "testid1", "testname1", "testpw1", Grade.CLIENT);

        // when

        // then
    }

}