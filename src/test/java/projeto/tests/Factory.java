package projeto.tests;


import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

import projeto.adapters.spring.entities.ClientEntity;
import projeto.adapters.spring.entities.OrderEntity;
import projeto.adapters.spring.entities.ProductEntity;
import projeto.domain.model.dtos.ClientDTO;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.model.dtos.ProductDTO;

public class Factory {
	
	public static ClientEntity createClient() {
		ClientEntity client = new ClientEntity(1L, "Rodrigo de Souza Paula");

		client.getOrders().add(createOrder1());
		client.getOrders().add(createOrder2());

		return client;
	}

	public static OrderEntity createOrder1() {
		OrderEntity orderEntity1 = new OrderEntity(1L, 1L, Date.from(Instant.now()), new BigDecimal("325.0"), "DONE");

		orderEntity1.getProducts().add(createProduct1());
		orderEntity1.getProducts().add(createProduct2());

		return orderEntity1;
	}

	public static OrderEntity createOrder2() {
		OrderEntity orderEntity2 = new OrderEntity(2L, 1L, Date.from(Instant.now()), new BigDecimal("2800.0"), "DONE");

		orderEntity2.getProducts().add(createProduct3());
		orderEntity2.getProducts().add(createProduct4());

		return orderEntity2;
	}

	public static ProductEntity createProduct1() {
		return new ProductEntity(1L, "Estojo", new BigDecimal("25.0"));
	}

	public static ProductEntity createProduct2() {
		return new ProductEntity(2L, "Teclado", new BigDecimal("300.0"));
	}

	public static ProductEntity createProduct3() {
		return new ProductEntity(3L, "PS5", new BigDecimal("2500.0"));
	}

	public static ProductEntity createProduct4() {
		return new ProductEntity(4L, "Mouse", new BigDecimal("300.0"));
	}

	public static ClientDTO createClientDTO() {
		ClientEntity client = createClient();
		return new ClientDTO(client, client.getOrders());
	}

	public static OrderDTO createOrderDTO() {
		OrderEntity order = createOrder1();
		return new OrderDTO(order, order.getProducts());
	}

	public static ProductDTO createProductDTO() {
		ProductEntity product = createProduct1();
		return new ProductDTO(product);
	}
}
