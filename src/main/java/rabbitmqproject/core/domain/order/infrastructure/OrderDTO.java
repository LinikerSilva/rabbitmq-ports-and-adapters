package rabbitmqproject.core.domain.order.infrastructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO implements Serializable {
  private Date date;
  private BigDecimal total;
}