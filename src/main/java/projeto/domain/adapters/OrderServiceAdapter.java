package projeto.domain.adapters;

import org.modelmapper.ModelMapper;
import projeto.adapters.spring.entities.OrderEntity;
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
  public OrderDTO create(OrderDTO orderDTO) {
    OrderEntity newOrder = modelMapper.map(orderDTO, OrderEntity.class);
    orderRepositoryPort.save(newOrder);
    System.out.printf("------>" + newOrder.getId());
    return new OrderDTO(newOrder);
  }
}
