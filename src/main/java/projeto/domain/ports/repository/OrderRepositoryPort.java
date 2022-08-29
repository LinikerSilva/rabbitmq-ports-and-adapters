package projeto.domain.ports.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import projeto.adapters.spring.entities.OrderEntity;

public interface OrderRepositoryPort {

  Optional<OrderEntity> findById(Long id);
  Page<OrderEntity> findAll(Pageable pageable);
  void save(OrderEntity order);
  OrderEntity getReferenceById(Long id);
  void deleteById(Long id);
}
