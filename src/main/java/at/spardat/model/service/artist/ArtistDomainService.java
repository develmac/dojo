package at.spardat.model.service.artist;

import at.spardat.model.domain.artist.Artist;
import at.spardat.model.domain.artist.ArtistTransformer;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.reposervice.ArtistRepoService;


@Service
public class ArtistDomainService {

    private final ArtistRepoService artistRepoService;

    @Autowired
    public ArtistDomainService(ArtistRepoService artistRepoService) {
        this.artistRepoService = artistRepoService;
    }

    public Single<Artist> artistsById(String id) {
        return artistRepoService
                .findById(id)
                .map(ArtistTransformer::modelFrom);
    }

    public Observable<Artist> artistsByName(String name) {
        return artistRepoService
                .findAllByName(name)
                .map(ArtistTransformer::modelFrom);
    }

    public Observable<Artist> artistsByNameLike(String name) {
        return artistRepoService
                .findAllByNameLike(name)
                .map(ArtistTransformer::modelFrom);
    }

}
