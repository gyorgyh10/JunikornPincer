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
            Customer customer1 = new Customer("A", "email", "password", "222-2222"
                    , new Address("Szeged", "Kecske utca", "19"));
            addressRepository.createAddressTable();
            customerRepository.createCustomerTable();
            restaurantRepository.createRestaurantTable();
            foodRepository.createFoodCategoriesTable();
            foodRepository.createFoodTable();
            ordersRepository.createOrdersTable();
            ordersRepository.createOrdersConnectorTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
