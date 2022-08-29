package projeto.domain.ports.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.domain.model.dtos.ProductDTO;

public interface ProductServicePort {

  ProductDTO findById(Long id);

  Page<ProductDTO> findAllPaged(Pageable pageable);

  ProductDTO create(ProductDTO productDTO);

//  ProductDTO update(Long id, ProductDTO productDTO);

  void delete(Long id);
}
