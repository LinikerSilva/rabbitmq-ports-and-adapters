package projeto.application.adapters.controllers;

import static projeto.config.utils.QueueUtils.EXCHANGE_NAME;
import static projeto.config.utils.QueueUtils.ORDER_QUEUE;
import static projeto.config.utils.QueueUtils.PRODUCT_QUEUE;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.config.utils.QueueUtils;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.ports.repository.OrderRepositoryPort;
import projeto.domain.ports.service.OrderServicePort;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

  private final OrderRepositoryPort orderRepositoryPort;

  private OrderServicePort orderServicePort;

  private RabbitTemplate rabbitTemplate;

  @GetMapping(value = "/{id}")
  public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
    OrderDTO dto = orderServicePort.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping
  public ResponseEntity<Page<OrderDTO>> findAll(Pageable pageable) {
    Page<OrderDTO> list = orderServicePort.findAllPaged(pageable);
    return ResponseEntity.ok().body(list);
  }

  @PostMapping
  public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
    orderDTO = orderServicePort.create(orderDTO);
    rabbitTemplate.convertAndSend(EXCHANGE_NAME, ORDER_QUEUE, orderDTO.getId());
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(orderDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(orderDTO);
  }

//  @PutMapping(value = "/{id}")
//  public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO dto) {
//    dto = orderServicePort.update(id, dto);
//    return ResponseEntity.ok().body(dto);
//  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    orderServicePort.delete(id);
    return ResponseEntity.noContent().build();
  }
}
