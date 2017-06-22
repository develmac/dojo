package at.spardat.rto;

import lombok.Value;

@Value(staticConstructor = "of")
public class ChatMsgRto {
    String name;
    String description;
}
