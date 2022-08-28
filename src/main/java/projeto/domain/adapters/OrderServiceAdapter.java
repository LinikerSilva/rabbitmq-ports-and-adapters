package projeto.domain.adapters;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.domain.adapters.exceptions.DatabaseException;
import projeto.domain.adapters.exceptions.ResourceNotFoundException;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.ports.repository.OrderRepositoryPort;
import projeto.domain.ports.service.OrderServicePort;

public class OrderServiceAdapter implements OrderServicePort {

  private final OrderRepositoryPort orderRepositoryPort;
  private final ModelMapper modelMapper = new ModelMapper();

  public OrderServiceAdapter(OrderRepositoryPort orderRepositoryPort) {
    this.orderRepositoryPort = orderRepositoryPort;
  }

  @Override
  public OrderDTO findById(Long id) {
    Optional<OrderEntity> obj = orderRepositoryPort.findById(id);
    OrderEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    entity.calculateOrderTotalValue();
    return new OrderDTO(entity, entity.getProducts());
  }

  @Override
  public Page<OrderDTO> findAllPaged(PageRequest pageRequest) {
    Page<OrderEntity> list = orderRepositoryPort.findAll(pageRequest);
    list.forEach(OrderEntity::calculateOrderTotalValue);
    return list.map(x -> new OrderDTO(x, x.getProducts()));
  }

  @Override
  public OrderDTO create(OrderDTO orderDTO) {
    OrderEntity newOrder = modelMapper.map(orderDTO, OrderEntity.class);
    newOrder.calculateOrderTotalValue();
    orderRepositoryPort.save(newOrder);
    System.out.printf("------>" + newOrder.getId());
    return new OrderDTO(newOrder, newOrder.getProducts());
  }

//  @Override
//  public OrderDTO update(Long id, OrderDTO orderDTO) {
//    try {
//      OrderEntity order = orderRepositoryPort.getReferenceById(id);
//      order = modelMapper.map(orderDTO, OrderEntity.class);
//      orderRepositoryPort.save(order);
//      return new OrderDTO(order, order.getProducts());
//    }
//    catch (EntityNotFoundException e) {
//      throw new ResourceNotFoundException("Id not found " + id);
//    }
//  }

  @Override
  public void delete(Long id) {
    try {
      orderRepositoryPort.deleteById(id);
    }
    catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }
}
