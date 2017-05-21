package at.spardat.model.domain.user;


import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {
    Integer id;

    public boolean equals(Integer id) {
        return this.id.equals(id);
    }
}
