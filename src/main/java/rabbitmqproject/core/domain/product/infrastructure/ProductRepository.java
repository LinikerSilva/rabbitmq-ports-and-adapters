package rabbitmqproject.core.domain.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rabbitmqproject.core.domain.product.core.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}