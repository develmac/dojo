package persistence.repo.room

import at.reactive.config.PersistenceConfig
import at.reactive.dao.RoomEntity
import at.reactive.repo.RoomRepo
import groovy.transform.TypeChecked
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class RoomRepoSpec extends Specification implements RoomRepoSpecSteps {

    @Autowired
    public RoomRepo roomRepo
    private static final String ANY_NAME = "any_name"

    def "should create chatMsg"() {
        given:
        "no rooms exist"()

        when:
        Try.of({
            new RoomEntity().setName(ANY_NAME)
        }).mapTry(roomRepo.&save)

        then:
        roomRepo.findAll().size() == 1
    }
}
