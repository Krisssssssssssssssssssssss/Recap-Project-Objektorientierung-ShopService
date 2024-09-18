import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            try {
            Product productToOrder = productRepo.getProductById(productId).get();
            products.add(productToOrder);}
            catch (Exception e) {
                System.out.println("Product with the ID: " + productId + " cannot be ordered! Error message: " + e.getMessage());
                return null;
            }
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }
    public void updateOrder(String orderId, OrderStatus newStatus) {
        orderRepo.getOrders().stream().filter(o -> o.id().equals(orderId)).findFirst().ifPresent(o -> {
            orderRepo.addOrder(o.withStatus(newStatus));
        });
    }

    public List<Order> getOrdersWithCertainStatus(OrderStatus orderStatus) {
        List<Order> orders = new ArrayList<>();
        orders.stream().filter(order -> order.status().equals(orderStatus)).forEach(order -> {
            orders.add(order);
        });
        return orders;
    }
}
