package projeto.domain.adapters;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
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
import projeto.adapters.spring.entities.OrderEntity;
import projeto.adapters.spring.repositories.OrderSpringRepository;
import projeto.domain.adapters.exceptions.DatabaseException;
import projeto.domain.adapters.exceptions.ResourceNotFoundException;
import projeto.domain.model.dtos.OrderDTO;
import projeto.tests.Factory;

@ExtendWith(SpringExtension.class)
class OrderServiceAdapterTest {

  @InjectMocks
  private OrderServiceAdapter service;

  @Mock
  private OrderSpringRepository repository;

  private long existingId;
  private long nonExistingId;
  private long dependentId;
  private OrderEntity order;
  private PageImpl<OrderEntity> page;
  private Long countTotalOrders;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 2000L;
    dependentId = 3L;
    countTotalOrders = 4L;
    order = Factory.createOrder1();
    page = new PageImpl<>(List.of(order));

    Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(order);

    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(order));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

    Mockito.doNothing().when(repository).deleteById(existingId);
    Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
  }

  @Test
  void findAllPagedShouldReturnPage() {

    Pageable pageable = PageRequest.of(0, 12);

    Page<OrderDTO> result = service.findAllPaged(pageable);

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

    Assertions.assertEquals(countTotalOrders, repository.count());
  }

  @Test
  void findAllPagedShouldReturnPageWhenPage0Size10() {

    PageRequest pageRequest = PageRequest.of(0, 12);

    Page<OrderDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(0, result.getNumber());
    Assertions.assertEquals(12, result.getSize());
    Assertions.assertEquals(countTotalOrders, result.getTotalElements());
  }

  @Test
  void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

    PageRequest pageRequest = PageRequest.of(50, 12);

    Page<OrderDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  void findAllPagedShouldReturnSortedPageWhenSortByTotal() {

    PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("total"));

    Page<OrderDTO> result = service.findAllPaged(pageRequest);

    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(new BigDecimal("325.0"), result.getContent().get(0).getTotal());
    Assertions.assertEquals(new BigDecimal("600.0"), result.getContent().get(1).getTotal());
    Assertions.assertEquals(new BigDecimal("2525.0"), result.getContent().get(2).getTotal());
    Assertions.assertEquals(new BigDecimal("2800.0"), result.getContent().get(3).getTotal());
  }
}
