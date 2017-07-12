package at.reactive.controller;


import at.reactive.chat.ChatMsgPushDomainService;
import at.reactive.chat.RoomDomainService;
import at.reactive.domain.chat.ChatMsg;
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

    private final ChatMsgPushDomainService chatMsgPushDomainService;
    private final RoomDomainService roomDomainService;

    @Autowired
    public ChatMsgPushCtrl(SimpMessagingTemplate messagingTemplate, ChatMsgPushDomainService chatMsgPushDomainService,
                           RoomDomainService roomDomainService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMsgPushDomainService = chatMsgPushDomainService;
        this.roomDomainService = roomDomainService;
        this.startListeningForchatMsgCreated();
    }

    @MessageMapping("/chatmsg")
    public void chatMsgReq(ChatMsgRto chatMsgRto) {

        Single<Room> roomSingle = roomDomainService.findByName(chatMsgRto.getRoom().getName()).firstOrError();

        Single<ChatMsg> chatMsgSingle = Single.just(chatMsgRto)
                .map(ChatMsgRtoTransformer::modelFrom);

        roomSingle
                .doOnError(throwable -> System.out.printf("ERROR: " + throwable))
                .subscribe(room -> roomDomainService.addMsgToRoom(chatMsgSingle, room));


    }

    private void startListeningForchatMsgCreated() {
        chatMsgPushDomainService
                .onNewChatMsg()
                .doOnNext(chatMsg -> System.out.printf("Sending new chatMsg event using WS!"))
                .map(ChatMsgRtoTransformer::toRto)
                .subscribe(this::sendChatMsgRto);
    }

    private void sendChatMsgRto(ChatMsgRto chatMsgRto) {
        messagingTemplate.convertAndSend("/topic/chatmsg", chatMsgRto);
    }

}
