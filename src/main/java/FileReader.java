import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileReader {
    public static void processCommand(String line, ShopService shopService, Map<String, String> aliasToOrderIdMap) {
        String[] tokens = line.split(" ");
        String command = tokens[0];

        switch (command) {
            case "addOrder":
                String alias = tokens[1];
                List<String> productIds = Arrays.asList(tokens).subList(2, tokens.length);
                Order newOrder = shopService.addOrder(productIds);
                if (newOrder != null) {
                    aliasToOrderIdMap.put(alias, newOrder.id());
                    System.out.println("Order added with alias: " + alias + ", ID: " + newOrder.id());
                } else {
                    System.out.println("Failed to add order with alias: " + alias);
                }
                break;

            case "setStatus":
                alias = tokens[1];
                OrderStatus newStatus = OrderStatus.valueOf(tokens[2].toUpperCase());
                String orderId = aliasToOrderIdMap.get(alias);
                if (orderId != null) {
                    shopService.updateOrder(orderId, newStatus);
                    System.out.println("Updated status of order with alias: " + alias + " to " + newStatus);
                } else {
                    System.out.println("Order with alias " + alias + " not found.");
                }
                break;

            case "printOrders":
                List<Order> orders = shopService.getOrderRepo().getOrders();
                orders.forEach(order -> System.out.println("Order ID: " + order.id() + ", Status: " + order.status() + ", Products: " + order.products()));
                break;

            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
