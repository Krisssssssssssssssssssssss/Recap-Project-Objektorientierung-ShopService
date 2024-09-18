import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo{
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Order addOrder(Order newOrder) {
        orders.add(newOrder);
        return newOrder;
    }

    public void removeOrder(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                orders.remove(order);
                return;
            }
        }
    }

    @Override
    public OrderStatus getOrderStatus(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order.status();
            }
        }
        return null;
    }

    @Override
    public void updateOrderStatus(String id, OrderStatus status) {
        orders.stream()
                .filter(order -> order.id().equals(id))
                .findFirst()
                .ifPresent(order -> {
                    Order updatedOrder = new Order(order.id(), order.products(), status);
                    int index = orders.indexOf(order);
                    orders.set(index, updatedOrder);
                });
    }
}
