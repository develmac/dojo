package at.reactive.reposervice;


import at.reactive.chat.ChatMsgRepoServicing;
import at.reactive.dao.ChatMsgEntity;
import at.reactive.domain.chat.ChatMsg;
import at.reactive.domain.room.Room;
import at.reactive.repo.ChatMsgRepo;
import at.reactive.transformer.ChatMsgEntityTransformer;
import at.reactive.transformer.RoomTransformer;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.vavr.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMsgRepoService implements ChatMsgRepoServicing {

    @Autowired
    private ChatMsgRepo chatMsgRepo;

    @Override
    public Observable<ChatMsg> findAllByName(String name) {
        return Observable.just(name)
                .map(chatMsgRepo::findAllByOrigin)
                .flatMap(Observable::fromIterable)
                .map(ChatMsgEntityTransformer::modelFrom);
    }

    @Override
    public Observable<ChatMsg> findAllByNameLike(String name) {
        return Observable.just(name)
                .map(chatMsgRepo::findAllByOriginLike)
                .flatMap(Observable::fromIterable)
                .map(ChatMsgEntityTransformer::modelFrom);
    }

    @Override
    public Single<ChatMsg> findById(String id) {
        return Single.just(id)
                .map(chatMsgRepo::findByChatmsgId)
                .map(ChatMsgEntityTransformer::modelFrom);
    }

    Observable<ChatMsg> findAllById(String id) {
        return Observable.just(id)
                .map(chatMsgRepo::findAllByRowId)
                .flatMap(Observable::fromIterable)
                .map(ChatMsgEntityTransformer::modelFrom);
    }

    @Override
    public Single<ChatMsg> save(ChatMsg model) {
        return Single.just(model)
                .map(ChatMsgEntityTransformer::entityFrom)
                .map(chatMsgRepo::save)
                .map(ChatMsgEntityTransformer::modelFrom)
                .doOnEvent((chatMsg, throwable) -> System.out.printf("saving chatMsg: " + chatMsg + " with exc " + throwable));
    }

    @Override
    public Single<ChatMsg> addMsgToRoom(ChatMsg chatMsg, Room room) {
        io.vavr.API.For(
                API.Try(() -> ChatMsgEntityTransformer.entityFrom(chatMsg)),
                API.Try(() -> RoomTransformer.toEntity(room)))
                .yield(ChatMsgEntity::setChatRoom)
                .forEach(chatMsgRepo::save);

        return Single.just(chatMsg);
    }


}
