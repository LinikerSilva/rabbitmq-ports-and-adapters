package projeto.domain.ports.repository;

import projeto.adapters.spring.entities.ProductEntity;

public interface ProductRepositoryPort {

  void save(ProductEntity produto);
}
