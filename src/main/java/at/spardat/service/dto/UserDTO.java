package at.spardat.service.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserDTO {
    final String username;
    final Integer id;
}
