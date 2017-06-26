package persistence.repo.room

import config.PersistenceConfig
import groovy.transform.TypeChecked
import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.RoomEntity
import persistence.repo.RoomRepo
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
