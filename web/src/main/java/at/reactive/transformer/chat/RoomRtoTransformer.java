package at.reactive.transformer.chat;

import at.reactive.domain.room.Room;
import at.reactive.rto.RoomRto;
import lombok.experimental.UtilityClass;

@UtilityClass
class RoomRtoTransformer {
    static RoomRto toRto(Room model) {
        return RoomRto.of(model.getName());
    }

    static Room fromRto(RoomRto roomRto) {
        return Room
                .builder()
                .name(roomRto.getName())
                .build();
    }
}
