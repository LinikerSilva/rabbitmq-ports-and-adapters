package projeto.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Client {

  private Long id;
  private String name;
  private Set<Order> orders = new HashSet<>();

  public Client() {
  }

  public Client(Long id, String name) {
    this.id = id;
    this.name = name;
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

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }
}
