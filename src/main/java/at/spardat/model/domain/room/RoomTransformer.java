package at.spardat.model.domain.room;

import persistence.dao.RoomEntity;

public class RoomTransformer {

    public static Room modelFromEntity(RoomEntity roomEntity) {
        return Room.builder().name(roomEntity.getName()).build();
    }

}
