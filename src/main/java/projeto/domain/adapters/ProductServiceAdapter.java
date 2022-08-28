package projeto.domain.adapters;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.domain.adapters.exceptions.DatabaseException;
import projeto.domain.adapters.exceptions.ResourceNotFoundException;
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
  public ProductDTO findById(Long id) {
    Optional<ProductEntity> obj = productRepositoryPort.findById(id);
    ProductEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new ProductDTO(entity);
  }

  @Override
  public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
    Page<ProductEntity> list = productRepositoryPort.findAll(pageRequest);
    return list.map(ProductDTO::new);
  }

  @Override
  public ProductDTO create(ProductDTO productDTO) {
    ProductEntity newProduct = modelMapper.map(productDTO, ProductEntity.class);
    productRepositoryPort.save(newProduct);
    System.out.printf("------>" + newProduct.getId());
    return new ProductDTO(newProduct);
  }

//  @Override
//  public ProductDTO update(Long id, ProductDTO productDTO) {
//    try {
//      ProductEntity product = productRepositoryPort.getReferenceById(id);
//      product = modelMapper.map(productDTO, ProductEntity.class);
//      productRepositoryPort.save(product);
//      return new ProductDTO(product);
//    }
//    catch (EntityNotFoundException e) {
//      throw new ResourceNotFoundException("Id not found " + id);
//    }
//  }

  @Override
  public void delete(Long id) {
    try {
      productRepositoryPort.deleteById(id);
    }
    catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }
}
