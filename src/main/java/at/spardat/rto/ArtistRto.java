package at.spardat.rto;

import lombok.Value;

@Value(staticConstructor = "of")
public class ArtistRto {
    String name;
    String description;
}
