package persistence.reposervice;


import io.reactivex.Observable;
import io.reactivex.Single;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ChatMsgEntity;
import persistence.repo.ChatMsgRepo;

@Service
public class ChatMsgRepoService {

    @Autowired
    private ChatMsgRepo chatMsgRepo;

    public Observable<ChatMsgEntity> findAllByName(String name) {
        return Option.of(name)
                .map(chatMsgRepo::findAllByOrigin)
                .map(Observable::fromIterable)
                .getOrElse(Observable::empty);
    }

    public Observable<ChatMsgEntity> findAllByNameLike(String name) {
        return Option.of(name)
                .map(chatMsgRepo::findAllByOriginLike)
                .map(Observable::fromIterable)
                .getOrElse(Observable::empty);
    }

    public Single<ChatMsgEntity> findById(String id) {
        return Option.of(id)
                .map(chatMsgRepo::findByChatmsgId)
                .map(Single::just)
                .getOrElse(Single::never);
    }

    Observable<ChatMsgEntity> findAllById(String id) {
        return Option.of(id)
                .map(chatMsgRepo::findAllByRowId)
                .map(Observable::fromIterable)
                .getOrElse(Observable::empty);
    }

    public ChatMsgEntity save(ChatMsgEntity entity) {
        return chatMsgRepo.save(entity);
    }
}
