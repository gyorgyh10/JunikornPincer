package JunicornPincer;

import JunicornPincer.Repositories.*;

import java.sql.SQLException;
import java.util.List;

public class JunikornPincer {
    private List<Restaurant> restaurantList;
    private List<Customer> customerList;
    private int deliveryPrice;
    private List<Orders> ordersList;

    public static void main(String[] args) {

        try (AddressRepository addressRepository = new AddressRepository();
             CustomerRepository customerRepository = new CustomerRepository();
             RestaurantRepository restaurantRepository = new RestaurantRepository();
             FoodRepository foodRepository = new FoodRepository();
             OrdersRepository ordersRepository = new OrdersRepository()) {
            Address address1 = new Address("Szeged", "Kecske utca", "19");
            Customer customer1 = new Customer("B", "email@gmail.com", "password", "222-2222"
                    , address1);
            addressRepository.createAddressTable();
            customerRepository.createCustomerTable();
            restaurantRepository.createRestaurantTable();
            foodRepository.createFoodCategoryTable();
            foodRepository.createFoodTable();
            ordersRepository.createOrdersTable();
            ordersRepository.createOrdersConnectorTable();
//            foodRepository.uploadFoodCategories();
            customerRepository.printAll();
            Address address2 = new Address ("Makó", "Fő utca", "15");
            Restaurant restaurant = new Restaurant("Zöldfa Étterem", address2, "333-3333", false);
//            restaurantRepository.insertRestaurant(restaurant);
            System.out.println(restaurantRepository.searchById(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
