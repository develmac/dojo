package at.reactive.reposervice;

import at.reactive.chat.ChatMsgRepoPushServicing;
import at.reactive.domain.chat.ChatMsg;
import io.reactivex.Observable;
import io.vavr.collection.List;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.sql.ROWID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.API.List;

@Service
@Log4j2
@Transactional
public class ChatMsgRepoPushService implements ChatMsgRepoPushServicing {

    private final ChatMsgDbChangeNotification chatMsgDbChangeNotification;

    private final ChatMsgRepoService chatMsgRepoService;

    @Autowired
    public ChatMsgRepoPushService(ChatMsgDbChangeNotification chatMsgDbChangeNotification, ChatMsgRepoService chatMsgRepoService) {
        this.chatMsgDbChangeNotification = chatMsgDbChangeNotification;
        this.chatMsgRepoService = chatMsgRepoService;
    }

    @Override
    public Observable<ChatMsg> startListeningForNewEntities() {
        return chatMsgDbChangeNotification
                .startListeningForNotifications()
                .flatMap(this::getRowIdForChangeEvent)
                .map(ROWID::stringValue)
                .flatMap(chatMsgRepoService::findAllById)
                .doOnError(err -> log.error("startListeningForNewEntities error'd", err));
    }

    private Observable<ROWID> getRowIdForChangeEvent(DatabaseChangeEvent changeEvent) {
        return Observable.fromIterable(
                List(changeEvent.getQueryChangeDescription())
                        .map(QueryChangeDescription::getTableChangeDescription)
                        .flatMap(List::of)
                        .map(TableChangeDescription::getRowChangeDescription)
                        .flatMap(List::of)
                        .map(RowChangeDescription::getRowid));

    }

}


