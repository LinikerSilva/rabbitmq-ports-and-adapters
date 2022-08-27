package rabbitmqproject.core.domain.product.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmqproject.core.domain.product.core.ports.ProductFacade;
import rabbitmqproject.core.domain.product.infrastructure.ProductDTO;

@RestController
@RequestMapping("/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

  private ProductFacade productFacade;

  @PostMapping
  public ResponseEntity create(@RequestBody ProductDTO productDTO) {
    productFacade.create(productDTO);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}