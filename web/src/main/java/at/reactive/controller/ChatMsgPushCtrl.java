package at.reactive.controller;


import at.reactive.chat.ChatMsgDomainService;
import at.reactive.chat.ChatMsgRepoPushServicing;
import at.reactive.chat.RoomDomainService;
import at.reactive.domain.room.Room;
import at.reactive.rto.ChatMsgRto;
import at.reactive.transformer.chat.ChatMsgRtoTransformer;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatMsgPushCtrl {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatMsgRepoPushServicing chatMsgNotificationService;
    private final RoomDomainService roomDomainService;
    private final ChatMsgDomainService chatMsgDomainService;

    @Autowired
    public ChatMsgPushCtrl(SimpMessagingTemplate messagingTemplate, ChatMsgRepoPushServicing chatMsgNotificationServicing,
                           RoomDomainService roomDomainService, ChatMsgDomainService chatMsgDomainService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMsgNotificationService = chatMsgNotificationServicing;
        this.roomDomainService = roomDomainService;
        this.chatMsgDomainService = chatMsgDomainService;
        this.startListeningForchatMsgCreated();
    }

    @MessageMapping("/chatmsg")
    public void chatMsgReq(ChatMsgRto chatMsgRto) {
        Single<Room> roomEntitySingle = roomDomainService.allRooms().firstOrError();

        Single.just(chatMsgRto)
                .map(ChatMsgRtoTransformer::modelFrom)
                .map(chatMsg -> chatMsg.setRoom(roomEntitySingle.blockingGet()))
                .map(chatMsgDomainService::save)
                .subscribe((chatMsgSingle, throwable) -> chatMsgSingle.subscribe());
    }

    private void startListeningForchatMsgCreated() {
        chatMsgNotificationService
                .startListeningForNewEntities()
                .doOnNext(chatMsg -> System.out.printf("Sending new chatMsg event using WS!"))
                .map(ChatMsgRtoTransformer::toRto)
                .subscribe(this::sendChatMsgRto);
    }

    private void sendChatMsgRto(ChatMsgRto chatMsgRto) {
        messagingTemplate.convertAndSend("/topic/chatmsg", chatMsgRto);
    }

}
