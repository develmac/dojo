package persistence.repo

import config.PersistenceConfig
import groovy.transform.TypeChecked
import javaslang.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ArtistEntity
import spock.lang.Specification

@ContextConfiguration(classes = [PersistenceConfig])
@TypeChecked
class ArtistRepoSpec extends Specification implements ArtistRepoSpecSteps {

    @Autowired
    public ArtistRepo artistRepo
    private static final String ANY_NAME = "any_name"

    def "should create artist"() {
        given:
        "no artists exist"()

        when:
        Try.of({
            new ArtistEntity().setName("any_name")
        }).mapTry(artistRepo.&save)

        then:
        artistRepo.findAllByName(ANY_NAME).size() == 1
    }


    def "should create many artists"() {
        given:
        "no artists exist"()

        when:
        int i = 0
        1000000.times {
            i++
            Try.of({
                new ArtistEntity().setName("any_name_$i")
            }).mapTry(artistRepo.&save)
        }

        then:
        artistRepo.findAllByName(ANY_NAME).size() == 1
    }

}
