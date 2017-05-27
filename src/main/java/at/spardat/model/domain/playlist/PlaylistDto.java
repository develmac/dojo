package at.spardat.model.domain.playlist;


import at.spardat.model.domain.user.User;
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
