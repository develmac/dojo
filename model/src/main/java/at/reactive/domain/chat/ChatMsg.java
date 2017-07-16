package at.reactive.domain.chat;

import at.reactive.domain.room.Room;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class ChatMsg {
    final String origin;
    final String text;
    Room room;
}
