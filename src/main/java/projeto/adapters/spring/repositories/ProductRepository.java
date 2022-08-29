package projeto.adapters.spring.repositories;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.domain.ports.repository.ProductRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductRepository implements ProductRepositoryPort {

  private final ProductSpringRepository productSpringRepository;

  @Override
  public Optional<ProductEntity> findById(Long id) {
    return productSpringRepository.findById(id);
  }

  @Override
  public Page<ProductEntity> findAll(Pageable pageable) {
    return productSpringRepository.findAll(pageable);
  }

  @Override
  public void save(ProductEntity produto) {
    productSpringRepository.save(produto);
  }

  @Override
  public ProductEntity getReferenceById(Long id) {
    return productSpringRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(Long id) {
    productSpringRepository.deleteById(id);
  }
}
