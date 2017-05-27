package at.spardat.model.domain.playlist;

import at.spardat.model.domain.user.User;
import lombok.Value;

@Value
public class Playlist {
    private final String name;
    private final String alias;
    private final User owner;
}
