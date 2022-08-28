package projeto.adapters.spring.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
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

  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "created_at")
  private Date createdAt;

  @Column(name = "total", nullable = false)
  private BigDecimal total;

  @PrePersist
  public void prePersist() {
    createdAt = Date.from(Instant.now());
  }
}
