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
  private Long client_id;
  private Date created_at;
  private BigDecimal total;
  private String status;
  private List<ProductDTO> products = new ArrayList<>();

  public OrderDTO() {
  }

  public OrderDTO(Long id, Long client_id, Date created_at, BigDecimal total, String status, List<ProductDTO> products) {
    this.id = id;
    this.client_id = client_id;
    this.created_at = created_at;
    this.total = total;
    this.status = status;
    this.products.addAll(products);
  }

  public OrderDTO(OrderEntity order) {
    this.id = order.getId();
    this.client_id = order.getClientId();
    this.created_at = order.getCreatedAt();
    this.total = order.getTotal();
    this.status = order.getStatus();
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

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<ProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDTO> products) {
    this.products = products;
  }

  public Long getClient_id() {
    return client_id;
  }

  public void setClient_id(Long client_id) {
    this.client_id = client_id;
  }
}
