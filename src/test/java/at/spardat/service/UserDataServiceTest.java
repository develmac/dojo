package at.spardat.service;

import at.spardat.service.dto.UserDTO;
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
public class UserDataServiceTest {

    @Autowired
    private UserDataService userService;


    @Test
    public void getUserByName() throws Exception {


        Single<UserDTO> result = userService.getUserById("123");

        TestObserver<UserDTO> userTestObserver = result.test();

        assertThat(userTestObserver.getEvents(), is(nullValue()));


    }

}