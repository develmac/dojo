package at.spardat.model.domain.artist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Artist {
    final String name;
    String description;
}
