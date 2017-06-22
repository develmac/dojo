package persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.dao.RoomEntity;

public interface RoomRepo extends JpaRepository<RoomEntity, String> {


}
