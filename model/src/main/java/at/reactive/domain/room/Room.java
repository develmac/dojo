package at.reactive.domain.room;


import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    String name;
    @Builder.Default
    public ReplaySubject<ChatMsg> allChatMsgs = ReplaySubject.createWithSize(1000);
    @Builder.Default
    public PublishSubject<ChatMsg> msgSubject = PublishSubject.create();


    public Observable<ChatMsg> addChatMsg(ChatMsg chatMsg) {
        chatMsg.setRoom(this);
        this.allChatMsgs.onNext(chatMsg);

        System.out.printf("EMITTING new chat msg to room: " + this);
        msgSubject.onNext(chatMsg);

        return Observable
                .create(emitter -> emitter.onNext(chatMsg));
    }

    public Observable<ChatMsg> startMsgObservation() {
        // return Observable.wrap(msgSubject);


        return msgSubject
                .flatMap(chatMsg -> Observable.<ChatMsg>create(e -> e.onNext(chatMsg)));
    }
}
