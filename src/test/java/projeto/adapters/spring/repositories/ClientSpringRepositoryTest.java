package projeto.adapters.spring.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import projeto.adapters.spring.entities.ClientEntity;
import projeto.tests.Factory;

@DataJpaTest
class ClientSpringRepositoryTest {

  @Autowired
  private ClientSpringRepository clientSpringRepository;

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
    ClientEntity client = Factory.createClient();
    client.setId(null);

    client = clientSpringRepository.save(client);
    Optional<ClientEntity> result = clientSpringRepository.findById(client.getId());

    Assertions.assertNotNull(client.getId());
    Assertions.assertEquals(countTotalProducts + 1L, client.getId());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertSame(result.get(), client);
  }

  @Test
  void deleteShouldDeleteObjectWhenIdExists() {
    clientSpringRepository.deleteById(existingId);

    Optional<ClientEntity> result = clientSpringRepository.findById(existingId);

    Assertions.assertFalse(result.isPresent());
  }

  @Test
  void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
      clientSpringRepository.deleteById(nonExistingId);
    });
  }
}