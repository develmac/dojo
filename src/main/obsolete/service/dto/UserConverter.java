package at.obsolete.service.dto;

import at.obsolete.model.domain.user.User;
import at.obsolete.model.domain.user.UserId;

public class UserConverter {


    public static User convertFromDTO(UserDTO userDTO) {
        return User.of(UserId.of(userDTO.getId()), userDTO.getUsername());
    }
}
