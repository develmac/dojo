package at.reactive.repo;

import at.reactive.dao.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<RoomEntity, String> {

    List<RoomEntity> findByName(String name);
}
