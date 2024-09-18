import java.util.List;

public interface OrderRepo {

    List<Order> getOrders();

    Order getOrderById(String id);

    Order addOrder(Order newOrder);

    void removeOrder(String id);

    OrderStatus getOrderStatus(String id);

    void updateOrderStatus(String id, OrderStatus status);
}
