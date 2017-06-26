package persistence.dao;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "ROOM", schema = "PLAYLISTSAPP")
public class RoomEntity {
    @Id
    @Column(name = "ID_ROOM", nullable = false, length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String roomId;
    @Basic
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private Collection<ChatMsgEntity> chatMsgesOfRoom;
}
