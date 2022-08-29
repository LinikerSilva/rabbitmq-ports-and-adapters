package projeto.adapters.spring.repositories;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.domain.ports.repository.OrderRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepository implements OrderRepositoryPort {

  private final OrderSpringRepository orderSpringRepository;

  @Override
  public Optional<OrderEntity> findById(Long id) {
    return orderSpringRepository.findById(id);
  }

  @Override
  public Page<OrderEntity> findAll(Pageable pageable) {
    return orderSpringRepository.findAll(pageable);
  }

  @Override
  public void save(OrderEntity order) {
    orderSpringRepository.save(order);
  }

  @Override
  public OrderEntity getReferenceById(Long id) {
    return orderSpringRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(Long id) {
    orderSpringRepository.deleteById(id);
  }
}
