package at.reactive.chat;


import at.reactive.domain.room.Room;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import room.RoomRepoServicing;


@Service
public class RoomDomainService {
    private final RoomRepoServicing roomRepoServicing;

    @Autowired
    public RoomDomainService(RoomRepoServicing roomRepoServicing) {
        this.roomRepoServicing = roomRepoServicing;
    }

    public Observable<Room> allRooms() {
        return roomRepoServicing
                .findAllRooms();
    }

    public Single<Room> save(Room room) {
        return roomRepoServicing.save(room);
    }

}
