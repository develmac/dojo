package persistence.repo;


import at.reactive.config.PersistenceConfig;
import at.reactive.dao.ChatMsgEntity;
import at.reactive.repo.ChatMsgRepo;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class ChatMsgRepoTest {

    @Autowired
    public ChatMsgRepo chatMsgRepo;

    @Test
    public void should_inject() {
        assertThat(chatMsgRepo, is(Matchers.notNullValue()));
    }

    @Test
    public void should_find_by_name() {
        List<ChatMsgEntity> result = chatMsgRepo.findAllByOrigin("mac");
        assertThat(result, Matchers.hasItem(chatMsgName(is("mac"))));
    }

    private FeatureMatcher<ChatMsgEntity, String> chatMsgName(Matcher<String> matcher) {
        return new FeatureMatcher<ChatMsgEntity, String>(matcher, "chatMsg origin",
                "chatMsg orign") {
            @Override
            protected String featureValueOf(ChatMsgEntity chatMsgEntity) {
                return chatMsgEntity.getOrigin();
            }
        };
    }

}
