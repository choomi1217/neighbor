package who.is.neighbor.chatRoom.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import who.is.neighbor.chatRoom.infrastructure.entity.ChatRoomEntity;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

import java.util.List;
import java.util.Optional;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByCitizen(CitizenEntity citizen);

    List<ChatRoomEntity> findByNeighbor(CitizenEntity citizen);

    @Query(" select cr from ChatRoomEntity cr where cr.citizen = :citizen and cr.neighbor = :neighbor ")
    Optional<ChatRoomEntity> findByCitizenAndNeighbor(CitizenEntity citizen, CitizenEntity neighbor);
}
