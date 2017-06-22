package at.spardat.model.service.chat;

import at.spardat.model.domain.chat.ChatMsg;
import at.spardat.model.domain.chat.ChatMsgTransformer;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.reposervice.ChatMsgRepoService;


@Service
public class ChatMsgDomainService {

    private final ChatMsgRepoService chatMsgRepoService;

    @Autowired
    public ChatMsgDomainService(ChatMsgRepoService chatMsgRepoService) {
        this.chatMsgRepoService = chatMsgRepoService;
    }

    public Single<ChatMsg> chatMsgsById(String id) {
        return chatMsgRepoService
                .findById(id)
                .map(ChatMsgTransformer::modelFrom);
    }

    public Observable<ChatMsg> chatMsgsByName(String name) {
        return chatMsgRepoService
                .findAllByName(name)
                .map(ChatMsgTransformer::modelFrom);
    }

    public Observable<ChatMsg> chatMsgsByNameLike(String name) {
        return chatMsgRepoService
                .findAllByNameLike(name)
                .map(ChatMsgTransformer::modelFrom);
    }

}
