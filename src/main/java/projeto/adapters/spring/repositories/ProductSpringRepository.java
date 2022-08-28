package projeto.adapters.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.adapters.spring.entities.ProductEntity;

@Repository
public interface ProductSpringRepository extends JpaRepository<ProductEntity, Long> {
}
