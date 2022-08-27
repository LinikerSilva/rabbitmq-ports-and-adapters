package rabbitmqproject.core.domain.order.core.ports;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rabbitmqproject.core.domain.order.core.model.Order;
import rabbitmqproject.core.domain.order.infrastructure.OrderDTO;
import rabbitmqproject.core.domain.order.infrastructure.OrderRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderFacade {
  private ModelMapper modelMapper;
  private OrderRepository orderRepository;

  public void create(OrderDTO orderDTO) {
    Order newOrder = modelMapper.map(orderDTO, Order.class);
    orderRepository.save(newOrder);
    System.out.printf("------>" + newOrder.getId());
  }
}