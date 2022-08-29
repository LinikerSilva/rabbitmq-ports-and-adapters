package projeto.config.utils;

public interface QueueUtils {

  String EXCHANGE_NAME = "amq-direct";

  String PRODUCT_QUEUE = "product-queue";
  String ORDER_QUEUE = "order-queue";
  String CLIENT_QUEUE = "client-queue";
}
