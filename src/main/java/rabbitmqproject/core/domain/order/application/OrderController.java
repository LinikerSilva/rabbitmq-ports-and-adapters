package rabbitmqproject.core.domain.order.application;

import java.awt.print.Pageable;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmqproject.core.domain.order.core.ports.OrderFacade;
import rabbitmqproject.core.domain.order.infrastructure.OrderDTO;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

  private OrderFacade orderFacade;

  @PostMapping
  public ResponseEntity create(@RequestBody OrderDTO orderDTO) {
    orderFacade.create(orderDTO);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}