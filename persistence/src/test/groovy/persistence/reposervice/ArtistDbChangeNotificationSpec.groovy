package persistence.reposervice

import config.PersistenceConfig
import groovy.transform.TypeChecked
import io.reactivex.Observable
import javaslang.control.Try
import oracle.jdbc.dcn.DatabaseChangeEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ArtistEntity
import persistence.repo.ArtistRepo
import persistence.repo.ArtistRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ArtistDbChangeNotificationSpec extends Specification implements ArtistRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 5)

    @Autowired
    private final ArtistDbChangeNotification artistDbChangeNotification

    @Autowired
    private final ArtistRepo artistRepo

    def "should inject"() {
        expect:
        artistDbChangeNotification
    }

    def "should notify on insert artist"() {
        given:
        "no artists exist"()
        Observable<DatabaseChangeEvent> observable =
                artistDbChangeNotification
                        .startListeningForNotifications()
                        .doOnError({ println "db notif on error $it" })
                        .doOnNext({ println "on next called" })

        def result
        observable.subscribe({ nextSignal ->
            result = nextSignal
        })

        when:
        Try.of({
            new ArtistEntity().setName("any_name")
        }).mapTry(artistRepo.&save)

        then:
        conditions.eventually {
            assert result
        }


    }
}
