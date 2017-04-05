package at.spardat.model.domain.User;


import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    final UserId userId;
    final String username;
}
