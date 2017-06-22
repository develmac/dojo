package at.spardat.model.domain.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMsg {
    final String origin;
    String text;
}
