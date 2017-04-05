package at.spardat.model.domain.User;


import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {
    Integer id;
}
