package rabbitmqproject.core.domain.product.infrastructure;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO implements Serializable {
  private String name;
  private BigDecimal value;
}