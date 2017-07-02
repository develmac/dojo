package at.reactive.chat;


import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChatMsgDomainService {

    private final ChatMsgRepoServicing chatMsgRepoServicing;

    @Autowired
    public ChatMsgDomainService(ChatMsgRepoServicing chatMsgRepoServicing) {
        this.chatMsgRepoServicing = chatMsgRepoServicing;
    }

    public Single<ChatMsg> chatMsgsById(String id) {
        return chatMsgRepoServicing
                .findById(id);

    }

    public Observable<ChatMsg> chatMsgsByName(String name) {
        return chatMsgRepoServicing
                .findAllByName(name);

    }

    public Observable<ChatMsg> chatMsgsByNameLike(String name) {
        return chatMsgRepoServicing
                .findAllByNameLike(name);
    }

    public Single<ChatMsg> save(ChatMsg chatMsg) {
        return chatMsgRepoServicing.save(chatMsg);
    }

}
