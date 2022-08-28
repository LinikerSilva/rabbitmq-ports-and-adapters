package projeto.domain.ports.service;

import projeto.domain.model.dtos.ProductDTO;

public interface ProductServicePort {

  ProductDTO create(ProductDTO produtoDTO);
}
