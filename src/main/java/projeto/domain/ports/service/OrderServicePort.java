package projeto.domain.ports.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.domain.model.dtos.OrderDTO;

public interface OrderServicePort {

  OrderDTO findById(Long id);

  Page<OrderDTO> findAllPaged(Pageable pageable);

  OrderDTO create(OrderDTO orderDTO);

//  OrderDTO update(Long id, OrderDTO orderDTO);

  void delete(Long id);
}
