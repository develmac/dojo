package at.reactive.reposervice;

import at.reactive.dao.RoomEntity;
import at.reactive.domain.room.Room;
import at.reactive.repo.RoomRepo;
import at.reactive.transformer.RoomTransformer;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import room.RoomRepoServicing;


@Service
public class RoomRepoService implements RoomRepoServicing {

    private final RoomRepo roomRepo;
    private final ChatMsgRepoService chatMsgRepoService;

    @Autowired
    public RoomRepoService(RoomRepo roomRepo, ChatMsgRepoService chatMsgRepo) {
        this.roomRepo = roomRepo;
        this.chatMsgRepoService = chatMsgRepo;
    }


    @Override
    public Single<Room> newWithName(String name) {
        return Single.fromCallable(() -> new RoomEntity().setName(name))
                .map(RoomTransformer::toModel);
    }

    @Override
    public Observable<Room> findAllRooms() {
        return Observable.
                fromIterable(roomRepo.findAll())
                .map(RoomTransformer::toModel);
    }

    @Override
    public Observable<Room> findAllByName(String name) {
        Observable<Room> roomObservable = Observable.
                fromIterable(roomRepo.findByName(name))
                .map(RoomTransformer::toModel)
                .doOnNext(this::onNewMsgInRoom);

        return roomObservable;
    }

    private void onNewMsgInRoom(Room room) {
        room.startMsgObservation()
                .flatMap(chatMsg -> chatMsgRepoService.save(chatMsg).toObservable())
                .subscribe();
    }

    @Override
    public Single<Room> save(Room room) {
        return Single.just(room)
                .map(RoomTransformer::toEntity)
                .map(roomRepo::save)
                .map(RoomTransformer::toModel);
    }

}
