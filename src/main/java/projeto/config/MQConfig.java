package projeto.config;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MQConfig {

  private AmqpAdmin amqpAdmin;

  private Queue queue(String queueName) {
    return new Queue(queueName, true, false, false);
  }

  private DirectExchange directExchange() {
    return new DirectExchange("amq-direct");
  }

  private Binding relate(Queue queue, DirectExchange directExchange) {
    return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange().getName(), queue.getName(), null);
  }

  @PostConstruct
  private void create() {
    Queue queue = queue("order-queue");
    DirectExchange directExchange = directExchange();
    Binding relate = relate(queue, directExchange);

    amqpAdmin.declareQueue(queue);
    amqpAdmin.declareExchange(directExchange);
    amqpAdmin.declareBinding(relate);
  }
}
