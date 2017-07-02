package at.reactive.transformer;

import at.reactive.dao.RoomEntity;
import at.reactive.domain.room.Room;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomTransformer {

    public static Room toModel(RoomEntity roomEntity) {
        return Room.builder().name(roomEntity.getName()).build();
    }

    public static RoomEntity toEntity(Room room) {
        return new RoomEntity().setName(room.getName());
    }

}
