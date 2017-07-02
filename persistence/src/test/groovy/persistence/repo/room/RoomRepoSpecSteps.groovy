package persistence.repo.room

import at.reactive.dao.RoomEntity
import at.reactive.repo.RoomRepo
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired

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