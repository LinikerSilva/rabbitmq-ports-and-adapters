package projeto.adapters.spring.repositories;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.domain.ports.repository.ProductRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductRepository implements ProductRepositoryPort {

  private final ProductSpringRepository productSpringRepository;

  @Override
  public void save(ProductEntity produto) {
    productSpringRepository.save(produto);
  }

  @Override
  public ProductEntity getReferenceById(Long id) {
    return productSpringRepository.getReferenceById(id);
  }
}
