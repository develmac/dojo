package persistence.repo;


import config.PersistenceConfig;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import persistence.dao.ArtistEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class ArtistRepoTest {

    @Autowired
    public ArtistRepo artistRepo;

    @Test
    public void should_inject() {
        assertThat(artistRepo, is(Matchers.notNullValue()));
    }

    @Test
    public void should_find_by_name() {
        List<ArtistEntity> result = artistRepo.findAllByName("mac");
        assertThat(result, Matchers.hasItem(artistName(is("mac"))));
    }

    private FeatureMatcher<ArtistEntity, String> artistName(Matcher<String> matcher) {
        return new FeatureMatcher<ArtistEntity, String>(matcher, "artist name",
                "artist name") {
            @Override
            protected String featureValueOf(ArtistEntity artistEntity) {
                return artistEntity.getName();
            }
        };
    }

}
