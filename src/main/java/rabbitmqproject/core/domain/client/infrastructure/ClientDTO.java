package rabbitmqproject.core.domain.client.infrastructure;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import rabbitmqproject.core.domain.order.core.model.Order;

@Getter
@Setter
public class ClientDTO implements Serializable {
  private String name;
  private Set<Order> orders;
}