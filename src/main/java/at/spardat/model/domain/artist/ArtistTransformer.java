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

    public static Artist from(ArtistRto artistRto) {
        return Artist.builder()
                .name(artistRto.getName())
                .description(artistRto.getDescription())
                .build();
    }

    public static ArtistEntity entityFrom(Artist artist) {
        return new ArtistEntity().setName(artist.getName()).setDescription(artist.getDescription());
    }
}
