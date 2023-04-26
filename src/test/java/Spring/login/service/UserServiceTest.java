package Spring.login.service;

import Spring.domain.login.entity.user.Grade;
import Spring.domain.login.entity.user.User;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    @Test
    void signup() {
        // given
        User user = new User(null, "testid1", "testname1", "testpw1", Grade.CLIENT);

        // when

        // then
    }

}