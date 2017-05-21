package persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.dao.ArtistEntity;

import java.util.List;

public interface ArtistRepo extends JpaRepository<ArtistEntity, String> {
    ArtistEntity findByName(String name);

    List<ArtistEntity> findAllByName(String name);


}
