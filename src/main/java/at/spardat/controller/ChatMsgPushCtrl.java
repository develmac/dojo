package at.spardat.controller;

import at.spardat.model.domain.chat.ChatMsgTransformer;
import at.spardat.rto.ChatMsgRto;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import persistence.dao.RoomEntity;
import persistence.reposervice.ChatMsgNotificationService;
import persistence.reposervice.ChatMsgRepoService;
import persistence.reposervice.RoomRepoService;

@Controller
public class ChatMsgPushCtrl {

    private final SimpMessagingTemplate template;

    private final ChatMsgNotificationService chatMsgNotificationService;

    private final ChatMsgRepoService chatMsgRepoService;

    private final RoomRepoService roomRepoService;

    @Autowired
    public ChatMsgPushCtrl(SimpMessagingTemplate template, ChatMsgNotificationService chatMsgNotificationService, ChatMsgRepoService chatMsgRepoService, RoomRepoService roomRepoService) {
        this.template = template;
        this.chatMsgNotificationService = chatMsgNotificationService;
        this.chatMsgRepoService = chatMsgRepoService;
        this.roomRepoService = roomRepoService;
        this.startListeningForchatMsgCreated();
    }

    @MessageMapping("/chatmsg")
    public void chatMsgReq(ChatMsgRto chatMsgRto) {
        Single<RoomEntity> roomEntitySingle = roomRepoService.findAllRooms().firstOrError();

        Single.just(chatMsgRto)
                .map(ChatMsgTransformer::modelFrom)
                .map(ChatMsgTransformer::entityFrom)
                .map(chatMsgEntity -> chatMsgEntity.setChatRoom(roomEntitySingle.blockingGet()))
                .subscribe(chatMsgRepoService::save);
    }

    private void startListeningForchatMsgCreated() {
        chatMsgNotificationService
                .startListeningForNewEntities()
                .map(ChatMsgTransformer::modelFrom)
                .map(ChatMsgTransformer::modelFrom)
                .doOnNext(chatMsgRto -> System.out.printf("Sending new chatMsg event using WS!"))
                .subscribe(this::sendChatMsgRto);


    }

    private void sendChatMsgRto(ChatMsgRto chatMsgRto) {
        template.convertAndSend("/topic/chatmsg", chatMsgRto);
    }

}
