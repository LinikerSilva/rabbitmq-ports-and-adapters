package projeto.application.adapters.controllers;

import java.net.URI;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.ports.service.OrderServicePort;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

  private OrderServicePort orderServicePort;

  @PostMapping
  public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
    orderDTO = orderServicePort.create(orderDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(orderDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(orderDTO);
  }
}
