package projeto.domain.ports.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import projeto.domain.model.dtos.OrderDTO;

public interface OrderServicePort {

  OrderDTO findById(Long id);

  Page<OrderDTO> findAllPaged(PageRequest pageRequest);

  OrderDTO create(OrderDTO orderDTO);

//  OrderDTO update(Long id, OrderDTO orderDTO);

  void delete(Long id);
}
