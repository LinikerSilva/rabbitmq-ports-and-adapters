package projeto.domain.model.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import projeto.adapters.spring.entities.OrderEntity;
import projeto.adapters.spring.entities.ProductEntity;

public class OrderDTO implements Serializable {
  private Long id;
  private Date date;
  private BigDecimal total;
  private List<ProductDTO> products = new ArrayList<>();

  public OrderDTO() {
  }

  public OrderDTO(Long id, Date date, BigDecimal total) {
    this.id = id;
    this.date = date;
    this.total = total;
  }

  public OrderDTO(OrderEntity order) {
    this.id = order.getId();
    this.date = order.getCreatedAt();
    this.total = order.getTotal();
  }

  public OrderDTO(OrderEntity entity, Set<ProductEntity> products) {
    this(entity);
    products.forEach(product -> this.products.add(new ProductDTO(product)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public List<ProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDTO> products) {
    this.products = products;
  }
}
