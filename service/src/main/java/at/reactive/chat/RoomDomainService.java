package at.reactive.chat;


import at.reactive.domain.chat.ChatMsg;
import at.reactive.domain.room.Room;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import room.RoomRepoServicing;


@Service
@Transactional
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

    public Observable<Room> findByName(String name) {
        return roomRepoServicing.findAllByName(name);
    }

    public Single<Room> save(Room room) {
        return roomRepoServicing.save(room);
    }

    public void addMsgToRoom(Single<ChatMsg> chatMsgSingle, Room room) {
        room.addChatMsg(chatMsgSingle.blockingGet());

    }
}
