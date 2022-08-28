package projeto.domain.model.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import projeto.adapters.spring.entities.ProductEntity;

public class ProductDTO implements Serializable {
  private Long id;
  private String name;
  private BigDecimal value;

  public ProductDTO(Long id, String name, BigDecimal value) {
    this.id = id;
    this.name = name;
    this.value = value;
  }

  public ProductDTO(ProductEntity product) {
    this.id = product.getId();
    this.name = product.getName();
    this.value = product.getValue();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }
}
