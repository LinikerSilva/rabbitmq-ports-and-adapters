package projeto.adapters.spring.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.tests.Factory;

@DataJpaTest
class ProductSpringRepositoryTest {

  @Autowired
  private ProductSpringRepository productSpringRepository;

  private long existingId;
  private long nonExistingId;
  private long countTotalProducts;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    countTotalProducts = 2L;
  }

  @Test
  void saveShouldPersistWithAutoincrementWhenIdIsNull() {
    ProductEntity Product = Factory.createProduct1();
    Product.setId(null);

    Product = productSpringRepository.save(Product);
    Optional<ProductEntity> result = productSpringRepository.findById(Product.getId());

    Assertions.assertNotNull(Product.getId());
    Assertions.assertEquals(countTotalProducts + 1L, Product.getId());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertSame(result.get(), Product);
  }

  @Test
  void deleteShouldDeleteObjectWhenIdExists() {
    productSpringRepository.deleteById(existingId);

    Optional<ProductEntity> result = productSpringRepository.findById(existingId);

    Assertions.assertFalse(result.isPresent());
  }

  @Test
  void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
      productSpringRepository.deleteById(nonExistingId);
    });
  }
}