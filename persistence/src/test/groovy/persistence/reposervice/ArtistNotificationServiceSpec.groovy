package persistence.reposervice

import config.PersistenceConfig
import groovy.transform.TypeChecked
import io.reactivex.Observable
import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ArtistEntity
import persistence.repo.ArtistRepo
import persistence.repo.ArtistRepoSpecSteps
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@TypeChecked
@ContextConfiguration(classes = [PersistenceConfig])
class ArtistNotificationServiceSpec extends Specification implements ArtistRepoSpecSteps {
    PollingConditions conditions = new PollingConditions(timeout: 15)

    @Autowired
    private ArtistNotificationService artistNotificationService

    @Autowired
    private ArtistRepo artistRepo

    def 'should get back new entity'() {
        given:
        "no artists exist"()

        and:
        Observable<ArtistEntity> observable =
                artistNotificationService
                        .startListeningForNewEntities()
                        .doOnError({ println "db notif on error $it" })
                        .doOnNext({ println "new artist entity found $it" })

        ArtistEntity artistEntity
        observable.subscribe({ ArtistEntity nextSignal ->
            artistEntity = nextSignal
        }, { println "KABOOM" })

        when:
        Try.of({
            new ArtistEntity().setName("any_name")
        }).mapTry(artistRepo.&save)

        then:
        conditions.eventually {
            assert artistEntity.getName() == "any_name"
        }

    }
}
