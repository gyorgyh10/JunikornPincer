package JunicornPincer.Repositories;

import JunicornPincer.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public void init() {
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
            Address address4 = new Address("Csongrád", "Progmasters utca", "22");
            Address address5 = new Address("Budapest", "Hollósi út", "2");
            Address address6 = new Address("Szeged", "Petőfi Sándor sgrt.", "81/a");
            Address address7 = new Address("Szeged", "Makkosházi krt.", "2");


//            (String name, Address address, String phoneNumber, boolean canDeliver)
            Restaurant restaurantJava = new Restaurant("Zöldfa Étterem", address, "333-3333", false);
            Restaurant restaurantJava2 = new Restaurant("Kékfa Étterem", address2, "444-4444", false);
            Restaurant restaurantJava3 = new Restaurant("Bajai Halászcsárda", address5, "777-7777", false);
            Restaurant restaurantJava4 = new Restaurant("Gringos Étterem", address6, "555-5555", false);
            Restaurant restaurantJava5 = new Restaurant("Burger King", address7, "666-6666", false);

            restaurantRepository.insertRestaurant(restaurantJava);
            restaurantRepository.insertRestaurant(restaurantJava2);
            restaurantRepository.insertRestaurant(restaurantJava3);
            restaurantRepository.insertRestaurant(restaurantJava4);
            restaurantRepository.insertRestaurant(restaurantJava5);

//            //String name, FoodCategory foodCategory, int price, Restaurant restaurant
            Restaurant restaurant1 = restaurantRepository.searchById(1);
            Restaurant restaurant2 = restaurantRepository.searchById(2);
            Restaurant restaurant3 = restaurantRepository.searchById(3);
            Restaurant restaurant4 = restaurantRepository.searchById(4);
            Restaurant restaurant5 = restaurantRepository.searchById(5);
//
            Food foodJava = new Food("leves", FoodCategory.LEVES, 333, restaurant1);
            Food foodJava2 = new Food("Mákosleves", FoodCategory.LEVES, 3333, restaurant1);
            Food foodJava3 = new Food("Pizza", FoodCategory.PIZZA, 444, restaurant2);
            Food foodJava4 = new Food("Sajtos Pizza", FoodCategory.PIZZA, 650, restaurant2);
            Food foodJava31 = new Food("Sushi", FoodCategory.EGZOTIKUS, 8010, restaurant3);
            Food foodJava32 = new Food("Melegszendvics", FoodCategory.ELŐÉTEL, 335, restaurant3);
            Food foodJava33 = new Food("Halas Pizza", FoodCategory.PIZZA, 2222, restaurant3);
            Food foodJava41 = new Food("Csipős Palacsinta", FoodCategory.DESSZERT, 467, restaurant4);
            Food foodJava42 = new Food("Bécsi szelet", FoodCategory.HÚSÉTELEK, 777, restaurant4);
            Food foodJava43 = new Food("Csíki sör", FoodCategory.ALKOHOLOS_ITAL, 4444, restaurant4);

            Food foodJava51 = new Food("Sajtos Burger", FoodCategory.HAMBURGER, 890, restaurant5);
            Food foodJava52 = new Food("Dupla Whopper Burger", FoodCategory.HAMBURGER, 1100, restaurant5);
            Food foodJava53 = new Food("Sajtos Burger", FoodCategory.HAMBURGER, 890, restaurant5);


            foodRepository.insertFood(foodJava);
            foodRepository.insertFood(foodJava2);
            foodRepository.insertFood(foodJava3);
            foodRepository.insertFood(foodJava4);
            foodRepository.insertFood(foodJava31);
            foodRepository.insertFood(foodJava32);
            foodRepository.insertFood(foodJava33);
            foodRepository.insertFood(foodJava41);
            foodRepository.insertFood(foodJava42);
            foodRepository.insertFood(foodJava43);
            foodRepository.insertFood(foodJava51);
            foodRepository.insertFood(foodJava52);
            foodRepository.insertFood(foodJava53);

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
            Orders orders2 = new Orders(dateSimple, foodList2, customer2);
            ordersRepository.insertOrders(orders1);
            ordersRepository.insertOrders(orders2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

