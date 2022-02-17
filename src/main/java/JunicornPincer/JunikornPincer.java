package JunicornPincer;

import JunicornPincer.Repositories.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

            addressRepository.createAddressTable();
            customerRepository.createCustomerTable();
            restaurantRepository.createRestaurantTable();
            foodRepository.createFoodCategoryTable();
            foodRepository.createFoodTable();
            ordersRepository.createOrdersTable();
            ordersRepository.createOrdersConnectorTable();
//            foodRepository.uploadFoodCategories();          //ha nincs semmink akkor csak 1x kell lefuttatni

//
//            //(String city, String street, String number)
//            Address address = new Address("Szeged", "Bor utca", "19");
//            Address address2 = new Address("Szeged-Tápé", "Honfoglalás utca", "15");
//            Address address3 = new Address("Szeged-Baktó", "Kokárda utca", "13");
//            Address address4 = new Address("csongrád", "Progmasters utca", "22");


//            (String name, Address address, String phoneNumber, boolean canDeliver)
//            Restaurant restaurant = new Restaurant("Zöldfa Étterem", address, "333-3333", false);
//            Restaurant restaurant2 = new Restaurant("Kékfa Étterem", address2, "444-4444", false);
//            restaurantRepository.insertRestaurant(restaurant);
//            restaurantRepository.insertRestaurant(restaurant2);

//            //String name, FoodCategory foodCategory, int price, Restaurant restaurant
            Restaurant restaurant1 = restaurantRepository.searchById(1);
            Restaurant restaurant2 = restaurantRepository.searchById(2);
//
//            Food food = new Food("leves", FoodCategory.LEVES, 333, restaurant1);
//            Food food2 = new Food("Mákosleves", FoodCategory.LEVES, 3333, restaurant1);
//            Food food3 = new Food("Pizza", FoodCategory.PIZZA, 444, restaurant2);
//            Food food4 = new Food("Sajtos Pizza", FoodCategory.PIZZA, 4444, restaurant2);


//            foodRepository.insertFood(food);
//            foodRepository.insertFood(food2);
//            foodRepository.insertFood(food3);
//            foodRepository.insertFood(food4);

//            //(String name, String email, String password, String phoneNumber, Address address)
//            Customer customer = new Customer("Szilárd", "vilagossz@vnet.hu", "ggPaSSwOrd", "123-4567", address3);
//            Customer customer1 = new Customer("Hajnalka", "Hajni@gmail.com", "xPassWordx", "123-345-456", address4);
//            customerRepository.insertCustomer(customer);
//            customerRepository.insertCustomer(customer1);
            Customer customer1 = customerRepository.searchById(1);
            Customer customer2 = customerRepository.searchById(2);


//            //Date date, List<Food> foodList, Customer customer)
            foodRepository.printAll();
            Date date = new Date(333);
            List<Food> foodList = new ArrayList<>();
            Food food2 = foodRepository.searchById(2);
            Food food6 = foodRepository.searchById(6);
            foodList.add(food2);
            foodList.add(food2);
            foodList.add(food6);
            List<Food> foodList2 = new ArrayList<>();
            Food food7 = foodRepository.searchById(7);
            Food food8 = foodRepository.searchById(8);
            foodList2.add(food7);
            foodList2.add(food7);
            foodList2.add(food7);
            foodList2.add(food8);
            java.sql.Date dateComplex = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            Date dateSimple = new Date(System.currentTimeMillis());
            Orders orders = new Orders(dateSimple, foodList2, customer1);
            ordersRepository.insertOrders(orders);

//
//            Junikorn();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void everythingMenu(RestaurantRepository restaurantRepository, FoodRepository foodRepository) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int menuNumber = 0;
        boolean finish = false;

        while (!finish) {
            showMenu();
            menuNumber = scanInt(scanner);
            if (menuNumber < 1 || menuNumber > 5) {
                System.out.println("Wrong number, please enter a valid number! ");
            }

            switch (menuNumber) {
                case 1:
                    boolean case1finish = false;
                    while (!case1finish) {
                        System.out.println("    1 - show all food");
                        System.out.println("    2 - show foods by food category");
                        System.out.println("    3 - Back");
                        System.out.println("    4 - Exit");
                        Integer foodMenuNumber = scanInt(scanner);
                        if (foodMenuNumber == 4) {
                            case1finish = true;
                            finish = true;
                        }
                        if (foodMenuNumber == 3) {
                            case1finish = true;
                        }

                        if (foodMenuNumber == 1) {
                            boolean foodnumber1finish = false;
                            while (!foodnumber1finish) {
                                System.out.println("choose a food to order: ");
                                foodRepository.printAll();                              //innentől ORDER FOOD
                                System.out.println();
                                foodnumber1finish=true;
                            }

                        }
                    }
                    break;

                case 2:
                    int restaurantNumber;
                    System.out.println();
                    System.out.println("Choose a restaurant:");
                    System.out.println();
                    restaurantRepository.printAll();
                    restaurantNumber = scanInt(scanner);
                    Restaurant restaurant = restaurantRepository.searchById(restaurantNumber);
                    System.out.println(restaurantRepository.allFoodsOfRestaurant(restaurant));
                    break;

                case 5:
                    finish = true;
                    break;

            }

        }
    }


    private static Integer scanInt(Scanner scanner) {
        int scannedInt = 0;
        try {
            scannedInt = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("hülyeség");
            scanInt(scanner);
        }
        return scannedInt;
    }


    private static void showMenu() {
        System.out.println();
        System.out.println("Please select a menu number: ");
        System.out.println("1 - Search food");
        System.out.println("2 - Search Restaurants");
        System.out.println("3 - Register");
        System.out.println("4 - Log in");
        System.out.println("5 - exit");
        System.out.println();
    }

    private static void Junikorn() {
        System.out.println();
        System.out.println();
        System.out.println("                                Welcome to JUNIKORNPINCÉR!");
        System.out.println();
        System.out.println();
        System.out.println("                  ,%%%,\n" +
                "                 ,%%%` %==--\n" +
                "                ,%%`( '|\n" +
                "               ,%%@ /\\_/\n" +
                "     ,%.-\"\"\"--%%% \"@@__\n" +
                "    %%/             |__`\\\n" +
                "   .%'\\     |   \\   /  //\n" +
                "   ,%' >   .'----\\ |  [/\n" +
                "      < <<`       ||\n" +
                "       `\\\\\\       ||\n" +
                "         )\\\\      )\\\n" +
                " ^^^jgs^^\"\"\"^^^^^^\"\"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
