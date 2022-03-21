package JunicornPincer;

import JunicornPincer.Repositories.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JunikornPincer {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
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

//            Database database = new Database();
//            database.init();
            Junikorn();
            everythingMenu(restaurantRepository, foodRepository, customerRepository, ordersRepository);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void showLoggedInMenu() {
        System.out.println("Please select a menu number: ");
        System.out.println("1 - Search food");
        System.out.println("2 - Search Restaurants");
        System.out.println("3 - Check your order list");
        System.out.println("4 - Change your data");
        System.out.println("5 - Log out");
        System.out.println("6 - Exit");
        System.out.println();
    }

    public static void loggedIn(Customer customer, CustomerRepository customerRepository, FoodRepository foodRepository,
                                RestaurantRepository restaurantRepository, OrdersRepository ordersRepository) {
        Scanner scanner = new Scanner(System.in);
        int menuNumber = 0;
        boolean finish = false;
        int totalPrice = 0;
        List<Food> foodList = new ArrayList<>();
        while (!finish) {
            System.out.println("Name: " + ANSI_RED + customer.getName() +
                    ANSI_RESET + "       email:" + ANSI_RED + customer.getEmail() + ANSI_RESET);
            showLoggedInMenu();
            menuNumber = scanInt(scanner);
            if (menuNumber < 1 || menuNumber > 6) {
                System.out.println("Wrong number, please enter a valid number! ");
            }

            switch (menuNumber) {
                case 1:
                    boolean case1finish = false;
                    while (!case1finish) {
                        System.out.println("    1 - Show all food");
                        System.out.println("    2 - Show foods by food category");
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
//                            while (!foodnumber1finish) {
                            int foodId = -1;
                            int quantity;

                            System.out.println("Choose foods to order: ");
                            foodRepository.printAll();                              //innentől ORDER FOOD
                            System.out.println("To finish order press 0");
                            while (foodId != 0) {
                                System.out.println("Give foodID:");
                                foodId = scanInt(scanner);
                                if (foodId == 0) {
                                    break;
                                }
                                System.out.println("Give quantity:");
                                quantity = scanInt(scanner);
                                if (foodId != 0) {
                                    Food food = foodRepository.searchById(foodId);
                                    for (int i = 1; i <= quantity; i++) {
                                        foodList.add(food);
                                        totalPrice += food.getPrice();
                                    }
                                }
                            }
                            System.out.println("Your order:");
                            System.out.println(foodList);
                            System.out.println("Total price: " + totalPrice);
                            case1finish = true;
                        }

                        if (foodMenuNumber == 2) {
                            int foodId;
                            int quantity;
                            boolean foodMenuNumber2finish = false;
                            while (!foodMenuNumber2finish) {
                                foodId = -1;
                                foodRepository.printAllFoodCategory();
                                System.out.println("Choose a number between 1-13: ");
                                int fc = scanInt(scanner);
                                if (fc == 0) {
                                    break;
                                }
                                foodRepository.searchAllFoodByFoodCategoryID(fc);
                                System.out.println("Choose foods to order: ");
                                System.out.println("To finish order press 0");
                                while (foodId != 0) {
                                    System.out.println("Give foodID:");
                                    foodId = scanInt(scanner);
                                    if (foodId == 0) {
                                        break;
                                    }
                                    System.out.println("Give quantity:");
                                    quantity = scanInt(scanner);
                                    if (foodId != 0) {
                                        Food food = foodRepository.searchById(foodId);
                                        for (int i = 1; i <= quantity; i++) {
                                            foodList.add(food);
                                            totalPrice += food.getPrice();
                                        }
                                    }
                                }

                                System.out.println("Press 0 to go back.");
                                if (fc == 0) {
                                    System.out.println("Your order:");
                                    System.out.println(foodList);
                                    System.out.println("Total price: " + totalPrice);
                                    foodMenuNumber2finish = true;
                                }
                            }
                        }
                    }
                    break;

                case 2:
                    boolean case2finish = false;
                    int foodId;
                    int quantity;
                    while (!case2finish) {
                        int restaurantNumber;
                        foodId = -1;
                        System.out.println();
                        System.out.println("Choose a restaurant or press 0 to go back:");
                        System.out.println();
                        restaurantRepository.printAll();
                        restaurantNumber = scanInt(scanner);
                        if (restaurantNumber != 0) {
                            Restaurant restaurant = restaurantRepository.searchById(restaurantNumber);
                            System.out.println(restaurantRepository.allFoodsOfRestaurant(restaurant));
                            System.out.println("Choose foods to order: ");
                            System.out.println("To finish order press 0");

                            while (foodId != 0) {
                                System.out.println("Give foodID:");
                                foodId = scanInt(scanner);
                                if (foodId == 0) {
                                    break;
                                }
                                System.out.println("Give quantity:");
                                quantity = scanInt(scanner);
                                if (foodId != 0) {
                                    Food food = foodRepository.searchById(foodId);
                                    for (int i = 1; i <= quantity; i++) {
                                        foodList.add(food);
                                        totalPrice += food.getPrice();
                                    }
                                }
                            }
                        }
                        if (restaurantNumber == 0) {
                            System.out.println("Your order:");
                            System.out.println(foodList);
                            System.out.println("Total price: " + totalPrice);
                            case2finish = true;
                        }
                    }
                    break;

                case 3:
                    boolean case3finish = false;
                    while (!case3finish) {
                        System.out.println("Your order:");
                        System.out.println(foodList);
                        totalPrice = 0;
                        for (Food food : foodList) {
                            totalPrice += food.getPrice();
                        }
                        System.out.println("Total price: " + totalPrice);
                        System.out.println("    1 - Finish order - Pay");
                        System.out.println("    2 - Change order");
                        System.out.println("    3 - Back");
                        int case3menu = scanInt(scanner);
                        if (case3menu == 1) {
                            Date date = new Date(System.currentTimeMillis());
                            Orders orders = new Orders(date, foodList, customer);
                            ordersRepository.insertOrders(orders);
                            printOrder(orders, totalPrice);
                            case3finish = true;
                            finish = true;
                        }
                        if (case3menu == 2) {
                            List<Food> toDelete = new ArrayList<>();
                            boolean deleteFinish = false;
                            while (!deleteFinish) {
                                System.out.println(foodList);
                                System.out.println("Give idFood to delete (0 to exit):");
                                int idFood = scanInt(scanner);
                                if (idFood == 0) {
                                    break;
                                }
                                System.out.println("Quantity to delete:");
                                int foodQuantity = scanInt(scanner);
                                for (int i = 1; i <= foodQuantity; i++) {
                                    Food food = foodRepository.searchById(idFood);
                                    toDelete.add(food);
                                    foodList.remove(food);
                                }
                            }
                        }
                        if (case3menu == 3) {
                            case3finish = true;
                        }
                    }

                    break;

                case 4:
                    boolean case4finish = false;
                    while (!case4finish) {
                        System.out.println("1 - Change your name");
                        System.out.println("2 - Change your email");
                        System.out.println("3 - Change your password");
                        System.out.println("4 - Change your phone number");
                        System.out.println("5 - Change your city");
                        System.out.println("6 - Change your street");
                        System.out.println("7 - Change your house number");
                        System.out.println("0 - Apply");
                        System.out.println();
                        System.out.println(customer);
                        int choice = scanInt(scanner);
                        switch (choice) {
                            case 1:
                                System.out.println("Give your name:");
                                String name = scanner.nextLine();
                                customer.setName(name);
                                break;
                            case 2:
                                System.out.println("Give your email:");
                                String email = scanner.nextLine();
                                customer.setEmail(email);
                                break;
                            case 3:
                                System.out.println("Give your password:");
                                String password = scanner.nextLine();
                                customer.setPassword(password);
                                break;
                            case 4:
                                System.out.println("Give your phone number:");
                                String phone = scanner.nextLine();
                                customer.setPhoneNumber(phone);
                                break;
                            case 5:
                                System.out.println("Give your city:");
                                String city = scanner.nextLine();
                                customer.getAddress().setCity(city);
                                break;
                            case 6:
                                System.out.println("Give your street:");
                                String street = scanner.nextLine();
                                customer.getAddress().setStreet(street);
                                break;
                            case 7:
                                System.out.println("Give your house number:");
                                String house = scanner.nextLine();
                                customer.getAddress().setNumber(house);
                                break;
                            case 0:
                                customerRepository.updateCustomerInfo(customer);
                                case4finish = true;
                                break;
                        }

                    }
                    break;
                case 5:
                    finish = true;
                    break;
                case 6:
                    System.exit(1);
            }
        }
    }

    private static void printOrder(Orders orders, int totalPrice) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("src/main/resources/bill" +
                        orders.getId() + ".txt"),
                StandardOpenOption.CREATE)) {
            Customer customer = orders.getCustomer();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(customer.getName() + "               " + customer.getAddress().getCity() + ", " +
                    customer.getAddress().getStreet() + ", " + customer.getAddress().getNumber());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            for (Food food : orders.getFoodList()) {
                bufferedWriter.write(food.getRestaurant().getName() + ":      " + food.getName() + " - " + food.getPrice() + "Ft");
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write("      Total price: " + totalPrice + "Ft");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Customer register(CustomerRepository customerRepository) {
        Customer customer = null;
        boolean good = false;
        Scanner scanner = new Scanner(System.in);
        while (!good) {
            System.out.println("Please enter your email address: ");
            String email = scanner.nextLine();
            while (customerRepository.emailExists(email)) {
                System.out.println("This email is already registered! Try again!");
                System.out.println("Please enter your email address: ");
                email = scanner.nextLine();
            }
            System.out.println("Please enter password: ");
            String password = scanner.nextLine();
            System.out.println("Please enter your name:");
            String name = scanner.nextLine();
            System.out.println("Please enter your phone number:");
            String phoneNumber = scanner.nextLine();
            System.out.println("___________ADDRESS:____________");
            System.out.println("Please enter your city:");
            String city = scanner.nextLine();
            System.out.println("Please enter your street:");
            String street = scanner.nextLine();
            System.out.println("Please enter your number:");
            String number = scanner.nextLine();
            customer = new Customer(name, email, password, phoneNumber, new Address(city, street, number));
            System.out.println("Is everything correct?(Y/N)");
            System.out.println(customer);
            String answer = scanner.nextLine();
            while ((!answer.equalsIgnoreCase("Y")) && (!answer.equalsIgnoreCase("N"))) {
                System.out.println("Wrong letter! Try again!");
                answer = scanner.nextLine();
            }
            if (answer.equalsIgnoreCase("Y")) {
                good = true;
            }
        }
        int id = customerRepository.insertCustomer(customer);
        return customerRepository.searchById(id);
    }

    public static Customer login(CustomerRepository customerRepository) {
        Customer customer = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email address: ");
        String email = scanner.nextLine();
        while (!customerRepository.emailExists(email)) {
            System.out.println("This email is NOT registered! Please give an other!");
            System.out.println("Please enter your email address: ");
            email = scanner.nextLine();
        }
        System.out.println("Please enter password: ");
        String password = scanner.nextLine();
        int counter = 0;
        customer = customerRepository.searchByEmail(email);
        while (!customer.getPassword().equals(password) && counter < 3) {
            System.out.println("Wrong password! Try again!");
            password = scanner.nextLine();
            counter++;
        }
        if (!customer.getPassword().equals(password)) {
            System.out.println("Check your email for an other password.");
            return null;
        }

        return customer;
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("Please select a menu number: ");
        System.out.println("1 - Search food");
        System.out.println("2 - Search Restaurants");
        System.out.println("3 - Register");
        System.out.println("4 - Log in");
        System.out.println("5 - exit");
        System.out.println();
    }

    public static void everythingMenu(RestaurantRepository restaurantRepository, FoodRepository foodRepository,
                                      CustomerRepository customerRepository, OrdersRepository ordersRepository) throws
            InterruptedException {
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
                                foodRepository.printAll();
                                System.out.println();
                                System.out.println("press 0 to go back ");
                                Integer back = scanInt(scanner);
                                if (back == 0) {
                                    foodnumber1finish = true;
                                }
                            }

                        }

                        if (foodMenuNumber == 2) {
                            boolean foodMenuNumber2finish = false;
                            while (!foodMenuNumber2finish) {
                                foodRepository.printAllFoodCategory();
                                System.out.println("Choose a number between 1-13: ");
                                int fc = scanInt(scanner);
                                foodRepository.searchAllFoodByFoodCategoryID(fc);
                                System.out.println("Press 0 to go back.");
                                if (fc == 0) {
                                    foodMenuNumber2finish = true;
                                }
                            }
                        }
                    }
                    break;

                case 2:
                    int restaurantNumber;
                    boolean case2finish = false;
                    while (!case2finish) {
                        System.out.println();
                        System.out.println("Choose a restaurant:");
                        System.out.println();
                        restaurantRepository.printAll();
                        System.out.println("Press 0 to go back.");
                        restaurantNumber = scanInt(scanner);

                        Restaurant restaurant = null;
                        try {
                            restaurant = restaurantRepository.searchById(restaurantNumber);
                            System.out.println(restaurantRepository.allFoodsOfRestaurant(restaurant));
                        } catch (NullPointerException e) {
                            System.out.println("Wrong number!");
                        }
                        if (restaurantNumber == 0) {
                            case2finish = true;
                        }
                    }
                    break;

                case 3:
                    Customer customer = register(customerRepository);
                    loggedIn(customer, customerRepository, foodRepository, restaurantRepository, ordersRepository);
                    break;
                case 4:
                    Customer customer1 = login(customerRepository);
                    if (customer1 == null) {
                        everythingMenu(restaurantRepository, foodRepository, customerRepository, ordersRepository);
                    } else {
                        loggedIn(customer1, customerRepository, foodRepository, restaurantRepository, ordersRepository);
                    }
                    break;
                case 5:
                    finish = true;
                    System.exit(0);
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
                " ^^^\"\"\"^^^^^^\"\"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
