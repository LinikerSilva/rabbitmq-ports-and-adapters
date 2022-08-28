package projeto.domain.ports.service;

import projeto.domain.model.dtos.OrderDTO;

public interface OrderServicePort {

  OrderDTO create(OrderDTO produtoDTO);
}
