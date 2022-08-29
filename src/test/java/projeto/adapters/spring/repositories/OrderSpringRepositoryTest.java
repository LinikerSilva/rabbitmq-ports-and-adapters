package projeto.adapters.spring.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.tests.Factory;

@DataJpaTest
class OrderSpringRepositoryTest {

  @Autowired
  private OrderSpringRepository orderSpringRepository;

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
    OrderEntity Order = Factory.createOrder1();
    Order.setId(null);

    Order = orderSpringRepository.save(Order);
    Optional<OrderEntity> result = orderSpringRepository.findById(Order.getId());

    Assertions.assertNotNull(Order.getId());
    Assertions.assertEquals(countTotalProducts + 1L, Order.getId());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertSame(result.get(), Order);
  }

  @Test
  void deleteShouldDeleteObjectWhenIdExists() {
    orderSpringRepository.deleteById(existingId);

    Optional<OrderEntity> result = orderSpringRepository.findById(existingId);

    Assertions.assertFalse(result.isPresent());
  }

  @Test
  void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
      orderSpringRepository.deleteById(nonExistingId);
    });
  }
}