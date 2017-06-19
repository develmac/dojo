package at.spardat.model.domain.chat;

import at.spardat.rto.ChatMsgRto;
import persistence.dao.ChatMsgEntity;

public class ChatMsgTransformer {

    public static ChatMsgRto modelFrom(ChatMsg chatMsg) {
        return ChatMsgRto.of(chatMsg.name, chatMsg.description);
    }

    public static ChatMsg modelFrom(ChatMsgEntity chatMsgEntity) {
        return ChatMsg.builder()
                .name(chatMsgEntity.getName())
                .description(chatMsgEntity.getDescription()).build();
    }

    public static ChatMsg modelFrom(ChatMsgRto chatMsgRto) {
        return ChatMsg.builder()
                .name(chatMsgRto.getName())
                .description(chatMsgRto.getDescription())
                .build();
    }

    public static ChatMsgEntity entityFrom(ChatMsg chatMsg) {
        return new ChatMsgEntity().setName(chatMsg.getName()).setDescription(chatMsg.getDescription());
    }
}
