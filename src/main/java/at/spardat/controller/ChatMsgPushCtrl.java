package at.spardat.controller;

import at.spardat.model.domain.chat.ChatMsgTransformer;
import at.spardat.rto.ChatMsgRto;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import persistence.reposervice.ChatMsgNotificationService;
import persistence.reposervice.ChatMsgRepoService;

@Controller
public class ChatMsgPushCtrl {

    private final SimpMessagingTemplate template;

    private final ChatMsgNotificationService chatMsgNotificationService;

    private final ChatMsgRepoService chatMsgRepoService;

    @Autowired
    public ChatMsgPushCtrl(SimpMessagingTemplate template, ChatMsgNotificationService chatMsgNotificationService, ChatMsgRepoService chatMsgRepoService) {
        this.template = template;
        this.chatMsgNotificationService = chatMsgNotificationService;
        this.chatMsgRepoService = chatMsgRepoService;
        this.startListeningForchatMsgCreated();
    }

    @MessageMapping("/chatmsg")
    public void chatMsgReq(ChatMsgRto chatMsgRto) {
        Try.of(() -> chatMsgRto)
                .map(ChatMsgTransformer::modelFrom)
                .map(ChatMsgTransformer::entityFrom)
                .mapTry(chatMsgRepoService::save)
                .onFailure(throwable -> System.out.printf("ERROR-> %s", throwable));
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
