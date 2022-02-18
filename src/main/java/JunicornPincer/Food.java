package JunicornPincer;

import java.util.List;
import java.util.Objects;

public class Food {
    private int id;
    private String name;
    private FoodCategory foodCategory;
    private int price;
//    private List<Integer> ratings;                  //SQLben JOIN (food->rating)
    private Restaurant restaurant;                   //SQLben FOREIGN KEY.

    public Food(int id, String name, FoodCategory foodCategory, int price, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.foodCategory = foodCategory;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Food(String name, FoodCategory foodCategory, int price, Restaurant restaurant) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.price = price;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

//    public List<Integer> getRatings() {
//        return ratings;
//    }

//    public void setRatings(List<Integer> ratings) {
//        this.ratings = ratings;
//    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id == food.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, foodCategory, price, restaurant);
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foodCategory=" + foodCategory +
                ", price=" + price +
//                ", ratings=" + ratings +
                ", restaurant=" + restaurant.getName() +
                '}' +" \n";
    }
}