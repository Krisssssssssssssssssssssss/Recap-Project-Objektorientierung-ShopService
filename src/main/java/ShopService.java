import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
public class ShopService {
    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private IdService idService;
    public ShopService(ProductRepo productRepo, OrderRepo orderRepo, IdService idService) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.idService = idService;
    }

    public OrderRepo getOrderRepo() {
        return orderRepo;
    }
    public ProductRepo getProductRepo() {
        return productRepo;
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            try {
            Product productToOrder = productRepo.getProductById(productId).get();
                if (productToOrder.quantity() <= 0) {
                    System.out.println("Product with the ID: " + productId + " is out of stock!");
                    return null;
                }
                Product updatedProduct = new Product(productToOrder.id(), productToOrder.name(), productToOrder.quantity() - 1);
                products.add(updatedProduct);}
            catch (Exception e) {
                System.out.println("Product with the ID: " + productId + " cannot be ordered! Error message: " + e.getMessage());
                return null;
            }
        }

        Order newOrder = new Order(idService.returnUUID().toString(), products, OrderStatus.PROCESSING);

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
    public Order getOldestOrderPerStatus(OrderStatus orderStatus) {
        Order oldestOrder = null;
        Instant oldestTimestamp = null;
        for (Order order : orderRepo.getOrders()) {
            if (order.status().equals(orderStatus)) {
                if (oldestOrder == null || order.timestamp().isBefore(oldestTimestamp)) {
                    oldestOrder = order;
                    oldestTimestamp = order.timestamp();
                }
            }
        }
        return oldestOrder;
    }
}
