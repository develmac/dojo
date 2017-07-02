package at.reactive.chat;

import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;

public interface ChatMsgRepoPushServicing {
    Observable<ChatMsg> startListeningForNewEntities();
}
