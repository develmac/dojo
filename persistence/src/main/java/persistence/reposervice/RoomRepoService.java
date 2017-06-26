package persistence.reposervice;

import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.RoomEntity;
import persistence.repo.RoomRepo;


@Service
public class RoomRepoService {

    private final RoomRepo roomRepo;

    @Autowired
    public RoomRepoService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }


    public RoomEntity newWithName(String name) {
        return roomRepo.save(new RoomEntity().setName(name));
    }

    public Observable<RoomEntity> findAllRooms() {
        return Observable.
                fromIterable(roomRepo.findAll());
    }

    public RoomEntity save(RoomEntity entity) {
        return roomRepo.save(entity);
    }
}
