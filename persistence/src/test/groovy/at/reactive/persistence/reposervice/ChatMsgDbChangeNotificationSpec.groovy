package at.reactive.persistence.reposervice

import at.reactive.config.PersistenceConfig
import at.reactive.dao.ChatMsgEntity
import at.reactive.repo.ChatMsgRepo
import at.reactive.repo.RoomRepo
import at.reactive.reposervice.ChatMsgDbChangeNotification
import groovy.transform.TypeChecked
import io.reactivex.Observable
import io.vavr.control.Try
import oracle.jdbc.dcn.DatabaseChangeEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import persistence.repo.msg.ChatMsgRepoSpecSteps
import persistence.repo.room.RoomRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ChatMsgDbChangeNotificationSpec extends Specification implements ChatMsgRepoSpecSteps, RoomRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 5)
    @Autowired
    private ChatMsgDbChangeNotification chatMsgDbChangeNotification

    @Autowired
    private ChatMsgRepo chatMsgRepo
    @Autowired
    private RoomRepo roomRepo
    @Autowired
    private PlatformTransactionManager transactionManager

    private static final String ANY_ORIGIN = "any_name"
    private static final String ANY_TEXT = "any_text"

    def "should inject"() {
        expect:
        chatMsgDbChangeNotification
    }

    def "should notify on insert chatMsg"() {
        given:
        "no chatMsgs exist"()
        and:
        "room with name fortune exists"().get()
        and:
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
        def transactionTemplate = new TransactionTemplate(transactionManager)

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Try.of({ roomRepo.findAll().first() })
                        .mapTry({ new ChatMsgEntity().setOrigin(ANY_ORIGIN).setChatRoom(it).setText(ANY_TEXT) })
                        .mapTry({ chatMsgRepo.save(it) })
                        .get()
            }
        })

        then:
        conditions.eventually {
            assert result
        }


    }
}
