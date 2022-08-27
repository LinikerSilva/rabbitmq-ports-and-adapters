package rabbitmqproject.core.domain.client.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rabbitmqproject.core.domain.client.core.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}