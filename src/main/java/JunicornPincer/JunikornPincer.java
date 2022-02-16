package JunicornPincer;

import JunicornPincer.Repositories.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
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
//            foodRepository.uploadFoodCategories();                                //ha nincs semmink akkor csak 1x kell lefuttatni
            Junikorn();
//            foodRepository.printAll();
//            Restaurant restaurant = restaurantRepository.searchById(2);
//            System.out.println(restaurantRepository.allFoodsOfRestaurant(restaurant));
            everythingMenu(restaurantRepository, foodRepository);


        } catch (SQLException | InterruptedException e) {
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
