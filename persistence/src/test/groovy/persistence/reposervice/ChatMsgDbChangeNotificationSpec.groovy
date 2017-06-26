package persistence.reposervice

import config.PersistenceConfig
import groovy.transform.TypeChecked
import io.reactivex.Observable
import javaslang.control.Try
import oracle.jdbc.dcn.DatabaseChangeEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ChatMsgEntity
import persistence.repo.ChatMsgRepo
import persistence.repo.msg.ChatMsgRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ChatMsgDbChangeNotificationSpec extends Specification implements ChatMsgRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 5)

    @Autowired
    private final ChatMsgDbChangeNotification chatMsgDbChangeNotification

    @Autowired
    private final ChatMsgRepo chatMsgRepo

    def "should inject"() {
        expect:
        chatMsgDbChangeNotification
    }

    def "should notify on insert chatMsg"() {
        given:
        "no chatMsgs exist"()
        Observable<DatabaseChangeEvent> observable =
                chatMsgDbChangeNotification
                        .startListeningForNotifications()
                        .doOnError({ println "db notif on error $it" })
                        .doOnNext({ println "on next called" })

        def result
        observable.subscribe({ nextSignal ->
            result = nextSignal
        })

        when:
        Try.of({
            new ChatMsgEntity().setOrigin("any_name")
        }).mapTry(chatMsgRepo.&save)

        then:
        conditions.eventually {
            assert result
        }


    }
}
