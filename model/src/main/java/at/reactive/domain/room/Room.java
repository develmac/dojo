package at.reactive.domain.room;


import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.vavr.collection.Seq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static io.vavr.collection.List.empty;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    String name;

    @Builder.Default
    Seq<ChatMsg> chatMsgs = empty();
    ObservableEmitter<ChatMsg> chatMsgEmitter;


    public Observable<ChatMsg> addChatMsg(ChatMsg chatMsg) {
        // this.chatMsgs.append(chatMsg);

        if (!Objects.isNull(chatMsgEmitter)) {
            System.out.printf("EMITTING new chat msg to room: " + this);
            chatMsgEmitter.onNext(chatMsg);
        }

        return Observable
                .create(emitter -> emitter.onNext(chatMsg));
    }

    public Observable<ChatMsg> startMsgObservation() {
        Observable<ChatMsg> objectObservable = Observable.create(chatMsgEmitter -> this.chatMsgEmitter = chatMsgEmitter);
        return objectObservable;
    }

}
