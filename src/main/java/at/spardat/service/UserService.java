package at.spardat.service;

import at.spardat.model.domain.User.User;
import at.spardat.model.domain.User.UserId;
import io.reactivex.Observable;
import io.reactivex.Single;
import javaslang.control.Option;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    public Observable<List<User>> getAllUsers() {
        return Observable.fromArray(USERS.get());
    }

    public Single<User> getUserByName(String userName) {
        return Observable.fromIterable(USERS.get())
                .filter(user -> user.getUsername().equals(userName))
                .firstOrError();
    }

    public Single<User> getUserById(String userName) {
        return Option.of(userName)
                .map(Integer::valueOf)
                .map((userId) ->
                        Observable.fromIterable(USERS.get())
                                .filter(user -> user.getUserId().equals(UserId.of(userId)))
                                .firstOrError())
                .getOrElse(Single.never());
    }

    private Supplier<List<User>> USERS =
            () -> Arrays.asList(User.of(UserId.of(12), "MaC"),
                    User.of(UserId.of(123), "Anton"));


}
