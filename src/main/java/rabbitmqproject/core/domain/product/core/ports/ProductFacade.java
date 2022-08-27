package rabbitmqproject.core.domain.product.core.ports;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rabbitmqproject.core.domain.product.core.model.Product;
import rabbitmqproject.core.domain.product.infrastructure.ProductDTO;
import rabbitmqproject.core.domain.product.infrastructure.ProductRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductFacade {

  private ModelMapper modelMapper;
  private ProductRepository productRepository;

  public void create(ProductDTO productDTO) {
    Product newProduct = modelMapper.map(productDTO, Product.class);
    productRepository.save(newProduct);
    System.out.printf("------>" + newProduct.getId());
  }
}
