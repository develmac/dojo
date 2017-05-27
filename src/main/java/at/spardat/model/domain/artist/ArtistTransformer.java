package at.spardat.model.domain.artist;

import at.spardat.rto.ArtistRto;

public class ArtistTransformer {

    public static ArtistRto from(Artist artist) {
        return ArtistRto.of(artist.name, artist.description);
    }


}
