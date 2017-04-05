package at.spardat.model.domain.Playlist;


import at.spardat.model.domain.User.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class PlaylistDto {
    private final String name;
    private final String alias;
    private final User owner;
}
