package projeto.config;

import static projeto.config.utils.QueueUtils.CLIENT_QUEUE;
import static projeto.config.utils.QueueUtils.EXCHANGE_NAME;
import static projeto.config.utils.QueueUtils.ORDER_QUEUE;
import static projeto.config.utils.QueueUtils.PRODUCT_QUEUE;

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
    return new DirectExchange(EXCHANGE_NAME);
  }

  private Binding relate(Queue queue, DirectExchange directExchange) {
    return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange().getName(), queue.getName(), null);
  }

  @PostConstruct
  private void create() {
    Queue productQueue = queue(PRODUCT_QUEUE);
    Queue orderQueue = queue(ORDER_QUEUE);
    Queue clientQueue = queue(CLIENT_QUEUE);

    DirectExchange directExchange = directExchange();

    Binding relateProduct = relate(productQueue, directExchange);
    Binding relateOrder = relate(orderQueue, directExchange);
    Binding relateClient = relate(clientQueue, directExchange);

    amqpAdmin.declareQueue(productQueue);
    amqpAdmin.declareQueue(orderQueue);
    amqpAdmin.declareQueue(clientQueue);

    amqpAdmin.declareExchange(directExchange);

    amqpAdmin.declareBinding(relateProduct);
    amqpAdmin.declareBinding(relateOrder);
    amqpAdmin.declareBinding(relateClient);
  }
}
