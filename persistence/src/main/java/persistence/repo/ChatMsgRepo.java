package persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import persistence.dao.ChatMsgEntity;

import java.util.List;

public interface ChatMsgRepo extends JpaRepository<ChatMsgEntity, String> {

    ChatMsgEntity findByChatmsgId(String id);

    List<ChatMsgEntity> findAllByOrigin(String origin);

    List<ChatMsgEntity> findAllByOriginLike(String origin);

    List<ChatMsgEntity> findAllByChatmsgId(String id);

    @Query(value = "SELECT * FROM CHATMSG WHERE ROWID IN (?1)", nativeQuery = true)
    List<ChatMsgEntity> findAllByRowId(String id);


}
