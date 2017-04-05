package at.spardat.service;

import at.spardat.model.domain.User.User;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserByName() throws Exception {

        Single<User> result = userService.getUserByName("MaC");

        TestObserver<User> userTestObserver = result.test();

        assertThat(userTestObserver.getEvents(), is(nullValue()));


    }

}