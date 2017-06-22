package persistence.repo

import config.PersistenceConfig
import groovy.transform.TypeChecked
import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ChatMsgEntity
import spock.lang.Specification

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ChatMsgRepoSpec extends Specification implements ChatMsgRepoSpecSteps {

    @Autowired
    public ChatMsgRepo chatMsgRepo
    private static final String ANY_NAME = "any_name"

    def "should create chatMsg"() {
        given:
        "no chatMsgs exist"()

        when:
        Try.of({
            new ChatMsgEntity().setOrigin("any_name")
        }).mapTry(chatMsgRepo.&save)

        then:
        chatMsgRepo.findAllByOrigin(ANY_NAME).size() == 1
    }


    def "should create many chatMsgs"() {
        given:
        "no chatMsgs exist"()

        when:
        int i = 0
        1000000.times {
            i++
            Try.of({
                new ChatMsgEntity().setOrigin("any_name_$i")
            }).mapTry(chatMsgRepo.&save)
        }

        then:
        chatMsgRepo.findAllByOrigin(ANY_NAME).size() == 1
    }

}
