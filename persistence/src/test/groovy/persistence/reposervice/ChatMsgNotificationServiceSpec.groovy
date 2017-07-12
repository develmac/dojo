package persistence.reposervice

import at.reactive.chat.ChatMsgRepoPushServicing
import at.reactive.config.PersistenceConfig
import at.reactive.domain.chat.ChatMsg
import at.reactive.repo.ChatMsgRepo
import groovy.transform.TypeChecked
import io.reactivex.Observable
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
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
        Try.of({
            ChatMsg.builder().origin("bla bla").build()
        }).mapTry(chatMsgRepo.&save)

        then:
        conditions.eventually {
            assert chatMsg.getOrigin() == "any_name"
        }

    }
}
