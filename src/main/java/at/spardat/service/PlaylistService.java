package at.spardat.service;

import at.spardat.model.domain.Playlist.Playlist;
import at.spardat.model.domain.User.User;
import at.spardat.model.domain.User.UserId;
import io.reactivex.Observable;
import io.reactivex.Single;
import javaslang.Function3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PlaylistService {

    final
    UserService userService;

    @Autowired
    public PlaylistService(UserService userService) {
        this.userService = userService;
    }

    public Single<List<Playlist>> getPlaylistForTest1() {
        return Single.just(ANY_PLAYLIST.get());
    }

    public Observable<Playlist> getPlayListForTest2() {
        return Observable.fromArray(ANY_PLAYLIST_ITEM.apply("bla1", "bla2", "mac"));
    }

    public Observable<Playlist> getPlayListForTest3() {
        return Observable.fromArray(ANY_PLAYLIST_ITEM.apply("bla1", "bla2", "mac"));
    }

    public Observable<Playlist> getPlayListForUser(User user) {
        return Observable.fromIterable(ANY_PLAYLIST.get())
                .filter(playlist -> playlist.getOwner().getUsername().equals(user.getUsername()));
    }

    public Observable<Playlist> getSlowPlaylist() {
        return Observable.<Playlist>create(emitter -> {

            for (int i = 0; i < 5; i++) {
                System.out.println("Emitting on thread " + Thread.currentThread().getName());
                Thread.sleep(1000);
                emitter.onNext(ANY_PLAYLIST_ITEM.apply("bla" + i, "bla2", "mac"));
            }
            emitter.onComplete();

        });
    }


    private Supplier<List<Playlist>> ANY_PLAYLIST =
            () -> Arrays.asList(ANY_PLAYLIST_ITEM.apply("any_name", "any_alias", "Anton"));

    private static Function3<String, String, String, Playlist> ANY_PLAYLIST_ITEM =
            (name, alias, ownerName) ->
                    new Playlist(name, alias, User.of(UserId.of(123), ownerName));


}
