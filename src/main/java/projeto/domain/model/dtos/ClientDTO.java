package projeto.domain.model.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import projeto.adapters.spring.entities.ClientEntity;
import projeto.adapters.spring.entities.OrderEntity;

public class ClientDTO implements Serializable {

  private Long id;
  private String name;
  private Set<OrderDTO> orders = new HashSet<>();

  public ClientDTO() {
  }

  public ClientDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public ClientDTO(ClientEntity client) {
    this.id = client.getId();
    this.name = client.getName();
  }

  public ClientDTO(ClientEntity entity, Set<OrderEntity> orders) {
    this(entity);
    orders.forEach(order -> this.orders.add(new OrderDTO(order, order.getProducts())));
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

  public Set<OrderDTO> getOrders() {
    return orders;
  }

  public void setOrders(Set<OrderDTO> orders) {
    this.orders = orders;
  }
}
