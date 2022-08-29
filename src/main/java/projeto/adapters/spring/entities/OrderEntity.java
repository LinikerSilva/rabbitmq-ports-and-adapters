package projeto.adapters.spring.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Column(name = "client_id")
  private Long clientId;

  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "created_at")
  private Date createdAt;

  @Column(name = "total", nullable = false)
  private BigDecimal total;

  @Column(name = "status")
  private String status;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "tb_order_product",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private Set<ProductEntity> products = new HashSet<>();

  @PrePersist
  public void prePersist() {
    createdAt = Date.from(Instant.now());
    total = BigDecimal.ZERO;
    calculateOrderTotalValue();
    status = "PROCESSING";
  }

  public void calculateOrderTotalValue() {
    Set<BigDecimal> list = this.products.stream().map(ProductEntity::getValue).collect(Collectors.toSet());
    BigDecimal orderTotalValue = BigDecimal.ZERO;
    for (BigDecimal productValue : list) {
      orderTotalValue = orderTotalValue.add(productValue);
    }
    this.total = orderTotalValue;
  }

  public OrderEntity(Long id, Long clientId, Date createdAt, BigDecimal total, String status) {
    this.id = id;
    this.clientId = clientId;
    this.createdAt = createdAt;
    this.total = total;
    this.status = status;
  }
}
