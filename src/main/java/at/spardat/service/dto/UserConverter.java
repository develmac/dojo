package at.spardat.service.dto;

import at.spardat.model.domain.user.User;
import at.spardat.model.domain.user.UserId;

public class UserConverter {


    public static User convertFromDTO(UserDTO userDTO) {
        return User.of(UserId.of(userDTO.getId()), userDTO.getUsername());
    }
}
