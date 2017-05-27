package at.spardat.model.domain.playlist;


public class PlaylistTransformer {
    public static PlaylistDto transformToDto(Playlist playlist) {
        return PlaylistDto
                .builder()
                .alias(playlist.getAlias())
                .name(playlist.getName())
                .build();
    }
}
