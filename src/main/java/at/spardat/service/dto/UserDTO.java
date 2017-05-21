package at.spardat.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
@RequiredArgsConstructor
public class UserDTO {
    final String username;
    final Integer id;
}
