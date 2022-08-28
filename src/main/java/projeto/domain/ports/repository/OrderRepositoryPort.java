package projeto.domain.ports.repository;

import projeto.adapters.spring.entities.OrderEntity;

public interface OrderRepositoryPort {

  void save(OrderEntity order);
}
