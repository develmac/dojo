package persistence.reposervice

import config.PersistenceConfig
import groovy.transform.TypeChecked
import io.reactivex.Observable
import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ChatMsgEntity
import persistence.repo.ChatMsgRepo
import persistence.repo.ChatMsgRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@TypeChecked
@ContextConfiguration(classes = [PersistenceConfig])
class ChatMsgNotificationServiceSpec extends Specification implements ChatMsgRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 15)

    @Autowired
    private ChatMsgNotificationService chatMsgNotificationService

    @Autowired
    private ChatMsgRepo chatMsgRepo

    def 'should get back new entity'() {
        given:
        "no chatMsgs exist"()

        and:
        Observable<ChatMsgEntity> observable =
                chatMsgNotificationService
                        .startListeningForNewEntities()
                        .doOnError({ println "db notif on error $it" })
                        .doOnNext({ println "new chatMsg entity found $it" })

        ChatMsgEntity chatMsgEntity
        observable.subscribe({ ChatMsgEntity nextSignal ->
            chatMsgEntity = nextSignal
        }, { println "KABOOM" })

        when:
        Try.of({
            new ChatMsgEntity().setName("any_name")
        }).mapTry(chatMsgRepo.&save)

        then:
        conditions.eventually {
            assert chatMsgEntity.getName() == "any_name"
        }

    }
}
