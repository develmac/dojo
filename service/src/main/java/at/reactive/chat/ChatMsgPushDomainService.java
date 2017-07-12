package at.reactive.chat;

import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMsgPushDomainService {

    @Autowired
    private ChatMsgRepoPushServicing chatMsgPushServicing;


    public Observable<ChatMsg> onNewChatMsg() {
        return chatMsgPushServicing.startListeningForNewEntities();
    }

}
