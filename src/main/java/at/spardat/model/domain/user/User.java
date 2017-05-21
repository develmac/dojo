package at.spardat.model.domain.user;


import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    final UserId userId;
    final String username;
}
