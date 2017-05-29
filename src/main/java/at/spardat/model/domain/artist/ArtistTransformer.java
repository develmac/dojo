package at.spardat.model.domain.artist;

import at.spardat.rto.ArtistRto;
import persistence.dao.ArtistEntity;

public class ArtistTransformer {

    public static ArtistRto from(Artist artist) {
        return ArtistRto.of(artist.name, artist.description);
    }

    public static Artist from(ArtistEntity artistEntity) {
        return Artist.builder()
                .name(artistEntity.getName())
                .description(artistEntity.getDescription()).build();
    }
}
