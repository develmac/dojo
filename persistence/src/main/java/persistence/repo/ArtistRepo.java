package persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import persistence.dao.ArtistEntity;

import java.util.List;

public interface ArtistRepo extends JpaRepository<ArtistEntity, String> {
    ArtistEntity findByName(String name);

    ArtistEntity findById(String id);

    List<ArtistEntity> findAllByName(String name);

    List<ArtistEntity> findAllById(String id);

    @Query(value = "SELECT * FROM ARTIST WHERE ROWID in (?1)", nativeQuery = true)
    List<ArtistEntity> findAllByRowId(String id);





}
