package persistence.dao;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "CHATMSG", schema = "PLAYLISTSAPP")
public class ChatMsgEntity {
    @Id
    @Column(name = "ID_CHATMSG", nullable = false, length = 36)
    private String chatmsgId;
    @Basic
    @Column(name = "ORIGIN", nullable = false, length = 200)
    private String origin;
    @Basic
    @Column(name = "TEXT", nullable = true, length = 4000)
    private String text;
    @Basic
    @Column(name = "FIP_DATE", nullable = true)
    private Timestamp fipDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ROOM")
    private RoomEntity chatRoom;

}
