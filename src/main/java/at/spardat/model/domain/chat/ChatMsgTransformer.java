package at.spardat.model.domain.chat;

import at.spardat.rto.ChatMsgRto;
import persistence.dao.ChatMsgEntity;

public class ChatMsgTransformer {

    public static ChatMsgRto modelFrom(ChatMsg chatMsg) {
        return ChatMsgRto.of(chatMsg.origin, chatMsg.text);
    }

    public static ChatMsg modelFrom(ChatMsgEntity chatMsgEntity) {
        return ChatMsg.builder()
                .origin(chatMsgEntity.getOrigin())
                .text(chatMsgEntity.getText()).build();
    }

    public static ChatMsg modelFrom(ChatMsgRto chatMsgRto) {
        return ChatMsg.builder()
                .origin(chatMsgRto.getName())
                .text(chatMsgRto.getDescription())
                .build();
    }

    public static ChatMsgEntity entityFrom(ChatMsg chatMsg) {
        return new ChatMsgEntity().setOrigin(chatMsg.getOrigin()).setText(chatMsg.getText());
    }
}
