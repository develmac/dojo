package at.reactive.chat;

import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMsgPushService {

    @Autowired
    private ChatMsgRepoPushServicing chatMsgPushServicing;


    public Observable<ChatMsg> onNewChatMsg() {
        return chatMsgPushServicing.startListeningForNewEntities();
    }

}
