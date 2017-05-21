package persistence.reposervice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ArtistEntity;
import persistence.repo.ArtistRepo;

import java.util.List;

@Service
public class ArtistRepoService {

    @Autowired
    public ArtistRepo artistRepo;

    public List<ArtistEntity> findAllByName(String name) {
        return artistRepo.findAllByName(name);
    }

    public ArtistEntity save(ArtistEntity entity) {
        return artistRepo.save(entity);
    }
}
