package at.reactive.transformer.chat;


import at.reactive.domain.chat.ChatMsg;
import at.reactive.rto.ChatMsgRto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatMsgRtoTransformer {

    public static ChatMsgRto toRto(ChatMsg chatMsg) {
        return ChatMsgRto.of(chatMsg.getOrigin(), chatMsg.getText());
    }

    public static ChatMsg modelFrom(ChatMsgRto chatMsgRto) {
        return ChatMsg.builder()
                .origin(chatMsgRto.getName())
                .text(chatMsgRto.getDescription())
                .build();
    }

}
