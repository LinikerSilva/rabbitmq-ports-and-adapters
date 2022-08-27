package rabbitmqproject.core.domain.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rabbitmqproject.core.domain.order.core.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}