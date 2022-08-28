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
import projeto.domain.model.dtos.ProductDTO;
import projeto.domain.ports.service.ProductServicePort;

@RestController
@RequestMapping("/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

  private ProductServicePort productServicePort;

  @PostMapping
  public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
    productDTO = productServicePort.create(productDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(productDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(productDTO);
  }
}
