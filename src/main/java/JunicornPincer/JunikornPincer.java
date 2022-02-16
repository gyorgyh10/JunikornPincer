package JunicornPincer;

import JunicornPincer.Repositories.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
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
//            foodRepository.uploadFoodCategories();                                //mindig csak 1x kell lefuttatni
//            customerRepository.printAll();
//            customerRepository.insertCustomer(customer1);
//            Address address2 = new Address ("Makó", "Fő utca", "15");
//            Restaurant restaurant = new Restaurant("Zöldfa Étterem", address2, "333-3333", false);
//            restaurantRepository.insertRestaurant(restaurant);
            Restaurant restaurant = restaurantRepository.searchById(1);
//            System.out.println(restaurant);
//            restaurant.setPhoneNumber("123-345-456");
//            restaurant.getAddress().setStreet("Bor utca");
//            System.out.println(restaurantRepository.allFoodsOfRestaurant(restaurant));
//            restaurantRepository.updateRestaurantInfo(restaurant);
//            System.out.println(restaurant);
//            restaurantRepository.printAll();
            Food food = foodRepository.searchById(2);
            Food food1 = foodRepository.searchById(1);
//            foodRepository.updateFoodInfo(food);
//            foodRepository.printAll();
            customerRepository.printAll();
            Customer customer = customerRepository.searchById(1);
//            customer.setName("Károly");
//            customer.getAddress().setCity("Budapest");
//            System.out.println();
//           customerRepository.updateCustomerInfo(customer);
//            customerRepository.printAll();

//            Date date=new Date(123414);
//            List<Food> listFood= new ArrayList<>();
//            listFood.add(food);
//            listFood.add(food);
//            listFood.add(food1);
//
//                Orders orders= new Orders(date,listFood, customer);
//            ordersRepository.insertOrders(orders);
            System.out.println(ordersRepository.searchById(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
