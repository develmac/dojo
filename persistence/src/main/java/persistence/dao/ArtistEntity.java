package persistence.dao;

import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ARTIST", schema = "PLAYLISTSAPP")
@Accessors(chain = true)
public class ArtistEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "NAME", nullable = false, length = 4000)
    private String name;
    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    private String description;
    @Basic
    @Column(name = "FIP_DATE", nullable = true)
    private Timestamp fipDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getFipDate() {
        return fipDate;
    }

    public void setFipDate(Timestamp fipDate) {
        this.fipDate = fipDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistEntity that = (ArtistEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (fipDate != null ? !fipDate.equals(that.fipDate) : that.fipDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fipDate != null ? fipDate.hashCode() : 0);
        return result;
    }
}
