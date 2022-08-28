package projeto.domain.adapters;

import org.modelmapper.ModelMapper;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.domain.model.dtos.ProductDTO;
import projeto.domain.ports.repository.ProductRepositoryPort;
import projeto.domain.ports.service.ProductServicePort;

public class ProductServiceAdapter implements ProductServicePort {

  private final ProductRepositoryPort productRepositoryPort;
  private final ModelMapper modelMapper = new ModelMapper();

  public ProductServiceAdapter(ProductRepositoryPort productRepositoryPort) {
    this.productRepositoryPort = productRepositoryPort;
  }

  @Override
  public ProductDTO create(ProductDTO produtoDTO) {
    ProductEntity newProduct = modelMapper.map(produtoDTO, ProductEntity.class);
    productRepositoryPort.save(newProduct);
    System.out.printf("------>" + newProduct.getId());
    return new ProductDTO(newProduct);
  }
}
