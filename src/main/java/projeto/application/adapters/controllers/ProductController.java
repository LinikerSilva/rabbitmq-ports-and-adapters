package projeto.application.adapters.controllers;


import java.net.URI;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.model.dtos.ProductDTO;
import projeto.domain.ports.service.ProductServicePort;

@RestController
@RequestMapping("/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

  private ProductServicePort productServicePort;

  private RabbitTemplate rabbitTemplate;

  @GetMapping(value = "/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
    ProductDTO dto = productServicePort.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction,
      @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
  ) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    Page<ProductDTO> list = productServicePort.findAllPaged(pageRequest);
    return ResponseEntity.ok().body(list);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
    productDTO = productServicePort.create(productDTO);
    rabbitTemplate.convertAndSend("amq-direct", "order-queue", productDTO.getId());
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(productDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(productDTO);
  }

//  @PutMapping(value = "/{id}")
//  public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
//    dto = productServicePort.update(id, dto);
//    return ResponseEntity.ok().body(dto);
//  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    productServicePort.delete(id);
    return ResponseEntity.noContent().build();
  }
}
