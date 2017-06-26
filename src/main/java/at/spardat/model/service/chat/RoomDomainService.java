package at.spardat.model.service.chat;

import at.spardat.model.domain.room.Room;
import at.spardat.model.domain.room.RoomTransformer;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.reposervice.RoomRepoService;


@Service
public class RoomDomainService {
    private final RoomRepoService roomRepoService;

    @Autowired
    public RoomDomainService(RoomRepoService roomRepoService) {
        this.roomRepoService = roomRepoService;
    }


    public Observable<Room> allRooms(String id) {
        return roomRepoService.findAllRooms()
                .map(RoomTransformer::modelFromEntity);

    }

}
