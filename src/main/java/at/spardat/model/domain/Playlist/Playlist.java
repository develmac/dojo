package at.spardat.model.domain.Playlist;

import at.spardat.model.domain.User.User;
import lombok.Value;

@Value
public class Playlist {
    private final String name;
    private final String alias;
    private final User owner;
}
