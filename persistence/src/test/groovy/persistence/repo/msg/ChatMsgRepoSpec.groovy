package persistence.repo.msg

import at.reactive.config.PersistenceConfig
import at.reactive.dao.ChatMsgEntity
import at.reactive.repo.ChatMsgRepo
import at.reactive.repo.RoomRepo
import groovy.transform.TypeChecked
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import persistence.repo.room.RoomRepoSpecSteps
import spock.lang.Specification

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ChatMsgRepoSpec extends Specification implements ChatMsgRepoSpecSteps, RoomRepoSpecSteps {

    @Autowired
    public ChatMsgRepo chatMsgRepo
    @Autowired
    public RoomRepo roomRepo

    private static final String ANY_ORIGIN = "any_name"

    @Transactional
    def "should create chatMsg"() {
        given:
        "no chatMsgs exist"()
        and:
        assert "room with name fortune exists"().isSuccess() == true
        when:
        Try.of({ roomRepo.findAll().first() })
                .mapTry({ new ChatMsgEntity().setOrigin(ANY_ORIGIN).setChatRoom(it) })
                .mapTry({
            chatMsgRepo.save(it)
        }).get()

        then:
        chatMsgRepo.findAllByOrigin(ANY_ORIGIN).size() == 1
    }
}
