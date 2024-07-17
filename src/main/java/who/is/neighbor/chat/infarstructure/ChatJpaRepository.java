package who.is.neighbor.chat.infarstructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.chat.infarstructure.entity.ChatEntity;

public interface ChatJpaRepository extends JpaRepository<ChatEntity, Long> {
}
