package room;

import at.reactive.domain.room.Room;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface RoomRepoServicing {
    Single<Room> newWithName(String name);

    Observable<Room> findAllRooms();

    Observable<Room> findAllByName(String name);

    Single<Room> save(Room room);
}
