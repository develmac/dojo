package persistence.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "ROOM", schema = "PLAYLISTSAPP")
public class RoomEntity {
    @Id
    @Column(name = "ID_ROOM", nullable = false, length = 36)
    private String roomId;
    @Basic
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private Collection<ChatMsgEntity> chatMsgesOfRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomEntity that = (RoomEntity) o;

        if (roomId != null ? !roomId.equals(that.roomId) : that.roomId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
