package at.reactive.domain.room;


import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.vavr.collection.Seq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    String name;
    Seq<ChatMsg> chatMsgs;

    Observable<ChatMsg> chatMsgObservable = Observable.empty();

    public Observable<ChatMsg> addChatMsg(ChatMsg chatMsg) {
        this.chatMsgs.append(chatMsg);

        this.chatMsgObservable.publish().connect();

        return Observable
                .create(emitter -> emitter.onNext(chatMsg));
    }

}
