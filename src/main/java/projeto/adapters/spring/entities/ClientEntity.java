package projeto.adapters.spring.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class ClientEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "client_id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(name = "tb_client_order",
      joinColumns = @JoinColumn(name = "client_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id"))
  private Set<OrderEntity> orders = new HashSet<>();

  public void calculateOrdersValues() {
    for (OrderEntity order : this.orders) {
      order.calculateOrderTotalValue();
    }
  }
}
