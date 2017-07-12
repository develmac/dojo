package at.reactive.transformer;

import at.reactive.dao.ChatMsgEntity;
import at.reactive.domain.chat.ChatMsg;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatMsgEntityTransformer {
    public static ChatMsg modelFrom(ChatMsgEntity chatMsgEntity) {
        return ChatMsg.builder()
                .room(RoomTransformer.toModel(chatMsgEntity.getChatRoom()))
                .origin(chatMsgEntity.getOrigin())
                .text(chatMsgEntity.getText()).build();
    }

    public static ChatMsgEntity entityFrom(ChatMsg chatMsg) {
        return new ChatMsgEntity().setOrigin(chatMsg.getOrigin()).setText(chatMsg.getText())
                .setChatRoom(RoomTransformer.toEntity(chatMsg.getRoom()));
    }
}
