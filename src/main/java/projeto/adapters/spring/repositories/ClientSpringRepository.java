package projeto.adapters.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.adapters.spring.entities.ClientEntity;

public interface ClientSpringRepository extends JpaRepository<ClientEntity, Long> {
}
