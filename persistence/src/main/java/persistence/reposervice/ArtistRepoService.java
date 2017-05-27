package persistence.reposervice;


import io.reactivex.Observable;
import io.reactivex.Single;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ArtistEntity;
import persistence.repo.ArtistRepo;

@Service
public class ArtistRepoService {

    @Autowired
    public ArtistRepo artistRepo;

    public Observable<ArtistEntity> findAllByName(String name) {
        return Option.of(name)
                .map(artistRepo::findAllByName)
                .map(Observable::fromIterable)
                .getOrElse(Observable::empty);
    }

    public Single<ArtistEntity> findById(String id) {
        return Option.of(id)
                .map(artistRepo::findById)
                .map(Single::just)
                .getOrElse(Single::never);
    }

    public Observable<ArtistEntity> findAllById(String id) {
        return Option.of(id)
                .map(artistRepo::findAllByRowId)
                .map(Observable::fromIterable)
                .getOrElse(Observable::empty);
    }

    public ArtistEntity save(ArtistEntity entity) {
        return artistRepo.save(entity);
    }
}
