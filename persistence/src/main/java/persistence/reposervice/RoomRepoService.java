package persistence.reposervice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.repo.RoomRepo;

@Service
public class RoomRepoService {

    private final RoomRepo roomRepo;

    @Autowired
    public RoomRepoService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }


}
