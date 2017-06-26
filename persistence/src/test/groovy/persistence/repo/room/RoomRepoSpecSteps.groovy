package persistence.repo.room

import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import persistence.dao.RoomEntity
import persistence.repo.RoomRepo

trait RoomRepoSpecSteps {

    @Autowired
    public RoomRepo roomRepo


    boolean "no rooms exist"() {
        roomRepo.deleteAll()
        assert roomRepo.findAll().isEmpty()
    }

    Try<RoomEntity> "room with name fortune exists"() {
        Try.of({ new RoomEntity().setName("fortune") })
                .map(roomRepo.&save)
    }
}