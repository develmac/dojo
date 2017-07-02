package room;

import at.reactive.domain.room.Room;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface RoomRepoServicing {
    Single<Room> newWithName(String name);

    Observable<Room> findAllRooms();

    Single<Room> save(Room room);
}
