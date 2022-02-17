package JunicornPincer.Repositories;

import JunicornPincer.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database {

   public void init(){
      try (AddressRepository addressRepository = new AddressRepository();
           CustomerRepository customerRepository = new CustomerRepository();
           RestaurantRepository restaurantRepository = new RestaurantRepository();
           FoodRepository foodRepository = new FoodRepository();
           OrdersRepository ordersRepository = new OrdersRepository()) {

         addressRepository.createAddressTable();
         customerRepository.createCustomerTable();
         restaurantRepository.createRestaurantTable();
         foodRepository.createFoodCategoryTable();
         foodRepository.createFoodTable();
         ordersRepository.createOrdersTable();
         ordersRepository.createOrdersConnectorTable();
         foodRepository.uploadFoodCategories();

//
//            //(String city, String street, String number)
            Address address = new Address("Szeged", "Bor utca", "19");
            Address address2 = new Address("Szeged-Tápé", "Honfoglalás utca", "15");
            Address address3 = new Address("Szeged-Baktó", "Kokárda utca", "13");
            Address address4 = new Address("csongrád", "Progmasters utca", "22");


//            (String name, Address address, String phoneNumber, boolean canDeliver)
            Restaurant restaurantJava = new Restaurant("Zöldfa Étterem", address, "333-3333", false);
            Restaurant restaurantJava2 = new Restaurant("Kékfa Étterem", address2, "444-4444", false);
            restaurantRepository.insertRestaurant(restaurantJava);
            restaurantRepository.insertRestaurant(restaurantJava2);

//            //String name, FoodCategory foodCategory, int price, Restaurant restaurant
         Restaurant restaurant1 = restaurantRepository.searchById(1);
         Restaurant restaurant2 = restaurantRepository.searchById(2);
//
            Food foodJava = new Food("leves", FoodCategory.LEVES, 333, restaurant1);
            Food foodJava2 = new Food("Mákosleves", FoodCategory.LEVES, 3333, restaurant1);
            Food foodJava3 = new Food("Pizza", FoodCategory.PIZZA, 444, restaurant2);
            Food foodJava4 = new Food("Sajtos Pizza", FoodCategory.PIZZA, 4444, restaurant2);


            foodRepository.insertFood(foodJava);
            foodRepository.insertFood(foodJava2);
            foodRepository.insertFood(foodJava3);
            foodRepository.insertFood(foodJava4);

//            //(String name, String email, String password, String phoneNumber, Address address)
            Customer customerJava = new Customer("Szilárd", "vilagossz@vnet.hu", "ggPaSSwOrd", "123-4567", address3);
            Customer customerJava2 = new Customer("Hajnalka", "Hajni@gmail.com", "xPassWordx", "123-345-456", address4);
            customerRepository.insertCustomer(customerJava);
            customerRepository.insertCustomer(customerJava2);
         Customer customer1 = customerRepository.searchById(1);
         Customer customer2 = customerRepository.searchById(2);


//            //Date date, List<Food> foodList, Customer customer)
         foodRepository.printAll();
         List<Food> foodList1 = new ArrayList<>();
         Food food1 = foodRepository.searchById(1);
         Food food2 = foodRepository.searchById(2);
         foodList1.add(food1);
         foodList1.add(food1);
         foodList1.add(food2);
         List<Food> foodList2 = new ArrayList<>();
         Food food3 = foodRepository.searchById(3);
         Food food4 = foodRepository.searchById(4);
         foodList2.add(food3);
         foodList2.add(food3);
         foodList2.add(food3);
         foodList2.add(food4);
         Date dateSimple = new Date(System.currentTimeMillis());
         Orders orders1 = new Orders(dateSimple, foodList1, customer1);
         Orders orders2= new Orders(dateSimple, foodList2, customer2);
         ordersRepository.insertOrders(orders1);
         ordersRepository.insertOrders(orders2);


      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

}

