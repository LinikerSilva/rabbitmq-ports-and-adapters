package projeto.adapters.spring.repositories;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.domain.ports.repository.OrderRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepository implements OrderRepositoryPort {

  private final OrderSpringRepository orderSpringRepository;

  @Override
  public void save(OrderEntity order) {
    orderSpringRepository.save(order);
  }
}
