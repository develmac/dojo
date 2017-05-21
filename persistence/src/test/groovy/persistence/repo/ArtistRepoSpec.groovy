package persistence.repo

import config.PersistenceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import persistence.dao.ArtistEntity
import persistence.reposervice.ArtistRepoService
import spock.lang.Specification

@ContextConfiguration(classes = [PersistenceConfig])
class ArtistRepoSpec extends Specification {

    @Autowired
    public ArtistRepo artistRepo

    @Autowired
    private ArtistRepoService artistRepoService


    def "should inject"() {
        expect:
        artistRepoService
    }

    def "should create playlist"() {
        given:
        artistRepo.findAllByName("any_name").size() == 0
        def newArtist = new ArtistEntity()
        newArtist.setName("any_name")
        when:
        artistRepo.save(newArtist)
        then:
        artistRepo.findAllByName("any_name").size() == 1

    }
}
