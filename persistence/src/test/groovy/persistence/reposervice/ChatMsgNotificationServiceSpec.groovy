package persistence.reposervice

import at.reactive.chat.ChatMsgRepoPushServicing
import at.reactive.config.PersistenceConfig
import at.reactive.dao.ChatMsgEntity
import at.reactive.domain.chat.ChatMsg
import at.reactive.repo.ChatMsgRepo
import at.reactive.repo.RoomRepo
import groovy.transform.TypeChecked
import io.reactivex.Observable
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import persistence.repo.msg.ChatMsgRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@TypeChecked
@ContextConfiguration(classes = [PersistenceConfig])
class ChatMsgNotificationServiceSpec extends Specification implements ChatMsgRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 15)

    @Autowired
    private ChatMsgRepoPushServicing repoPushService
    @Autowired
    private ChatMsgRepo chatMsgRepo
    @Autowired
    private RoomRepo roomRepo
    @Autowired
    private PlatformTransactionManager transactionManager

    private static final String ANY_ORIGIN = "any_name"
    private static final String ANY_TEXT = "any_text"

    def 'should get back new entity'() {
        given:
        "no chatMsgs exist"()

        and:
        Observable<ChatMsg> observable =
                repoPushService
                        .startListeningForNewEntities()
                        .doOnError({ println "db notif on error $it" })
                        .doOnNext({ println "new chatMsg entity found $it" })

        ChatMsg chatMsg
        observable.subscribe({ ChatMsg nextSignal ->
            chatMsg = nextSignal
        }, { println "KABOOM" })

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
            assert chatMsg.getOrigin() == "any_name"
        }

    }
}
