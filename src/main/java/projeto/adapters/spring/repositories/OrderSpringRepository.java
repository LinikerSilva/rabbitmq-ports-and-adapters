package projeto.adapters.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.adapters.spring.entities.OrderEntity;

@Repository
public interface OrderSpringRepository extends JpaRepository<OrderEntity, Long> {
}
