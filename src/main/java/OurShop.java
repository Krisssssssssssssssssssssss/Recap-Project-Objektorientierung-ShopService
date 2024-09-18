import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class OurShop {
    public static void main(String[] args) {

        OrderRepo orderRepo = new OrderListRepo();
        ProductRepo productRepo = new ProductRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo, new IdService());

        Product product1 = new Product("1", "Bike", 5);
        Product product2 = new Product("2", "Tomato", 5);
        Product product3 = new Product("3", "65 square meter apartment", 5);

        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);

        List<String> productIds =  shopService.getProductRepo().getProducts().stream().map(Product::id).collect(Collectors.toList());
        shopService.addOrder(productIds);

       shopService.getOrderRepo().getOrders().forEach(System.out::println);
       System.out.println(shopService.getOldestOrderPerStatus(OrderStatus.PROCESSING).products());

        System.out.println();
        System.out.println();


        Map<String, String> aliasToOrderIdMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("transactions.txt"));

            for (String line : lines) {
                FileReader.processCommand(line, shopService, aliasToOrderIdMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}