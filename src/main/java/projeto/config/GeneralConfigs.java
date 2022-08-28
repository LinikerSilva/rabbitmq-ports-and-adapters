package projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projeto.domain.adapters.ClientServiceAdapter;
import projeto.domain.adapters.OrderServiceAdapter;
import projeto.domain.adapters.ProductServiceAdapter;
import projeto.domain.ports.repository.ClientRepositoryPort;
import projeto.domain.ports.repository.OrderRepositoryPort;
import projeto.domain.ports.repository.ProductRepositoryPort;
import projeto.domain.ports.service.ClientServicePort;
import projeto.domain.ports.service.OrderServicePort;
import projeto.domain.ports.service.ProductServicePort;

@Configuration
public class GeneralConfigs {

  @Bean
  ProductServicePort productService(ProductRepositoryPort productRepositoryPort) {
    return new ProductServiceAdapter(productRepositoryPort);
  }

  @Bean
  OrderServicePort orderService(OrderRepositoryPort orderRepositoryPort) {
    return new OrderServiceAdapter(orderRepositoryPort);
  }

  @Bean
  ClientServicePort clientService(ClientRepositoryPort clientRepositoryPort) {
    return new ClientServiceAdapter(clientRepositoryPort);
  }
}
