package at.spardat.model.domain.room;

import at.spardat.model.domain.chat.ChatMsg;
import javaslang.collection.Seq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    String name;
    Seq<ChatMsg> chatMsgs;

}
