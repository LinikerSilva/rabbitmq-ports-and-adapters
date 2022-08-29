package projeto.domain.adapters;

import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.adapters.spring.repositories.ProductSpringRepository;
import projeto.domain.adapters.exceptions.DatabaseException;
import projeto.domain.adapters.exceptions.ResourceNotFoundException;
import projeto.domain.model.dtos.ProductDTO;
import projeto.tests.Factory;

@ExtendWith(SpringExtension.class)
class ProductServiceAdapterTest {

  @InjectMocks
  private ProductServiceAdapter service;

  @Mock
  private ProductSpringRepository repository;

  private long existingId;
  private long nonExistingId;
  private long dependentId;
  private ProductEntity product;
  private PageImpl<ProductEntity> page;
  private Long countTotalProducts;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 2000L;
    dependentId = 3L;
    countTotalProducts = 4L;
    product = Factory.createProduct1();
    page = new PageImpl<>(List.of(product));

    Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

    Mockito.doNothing().when(repository).deleteById(existingId);
    Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
  }

  @Test
  void findAllPagedShouldReturnPage() {

    Pageable pageable = PageRequest.of(0, 12);

    Page<ProductDTO> result = service.findAllPaged(pageable);

    Assertions.assertNotNull(result);

    Mockito.verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void deleteShouldThrowDatabaseExceptionWhenDependentId() {

    Assertions.assertThrows(DatabaseException.class, () -> {
      service.delete(dependentId);
    });

    Mockito.verify(repository, times(1)).deleteById(dependentId);
  }

  @Test
  void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(nonExistingId);
    });

    Mockito.verify(repository, times(1)).deleteById(nonExistingId);
  }

  @Test
  void deleteShouldDoNothingWhenIdExists() {

    Assertions.assertDoesNotThrow(() -> {
      service.delete(existingId);
    });

    Mockito.verify(repository, times(1)).deleteById(existingId);
  }

  @Test
  void deleteShouldDeleteResourceWhenIdExists() {

    service.delete(existingId);

    Assertions.assertEquals(countTotalProducts, repository.count());
  }

  @Test
  void findAllPagedShouldReturnPageWhenPage0Size10() {

    PageRequest pageRequest = PageRequest.of(0, 12);

    Page<ProductDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(0, result.getNumber());
    Assertions.assertEquals(12, result.getSize());
    Assertions.assertEquals(countTotalProducts, result.getTotalElements());
  }

  @Test
  void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

    PageRequest pageRequest = PageRequest.of(50, 12);

    Page<ProductDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  void findAllPagedShouldReturnSortedPageWhenSortByName() {

    PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("name"));

    Page<ProductDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals("Estojo", result.getContent().get(0).getName());
    Assertions.assertEquals("Mouse", result.getContent().get(1).getName());
    Assertions.assertEquals("PS5", result.getContent().get(2).getName());
    Assertions.assertEquals("Teclado", result.getContent().get(3).getName());
  }
}
