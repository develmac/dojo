package at.reactive.chat;

import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface ChatMsgRepoServicing {
    Observable<ChatMsg> findAllByName(String name);

    Observable<ChatMsg> findAllByNameLike(String name);

    Single<ChatMsg> findById(String id);

    Single<ChatMsg> save(ChatMsg entity);
}
