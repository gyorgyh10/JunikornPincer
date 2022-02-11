package JunicornPincer;

import java.util.List;

public class Food {
    private int id;
    private String name;
    private FoodCategories foodCategories;
    private int price;
    private List<Integer> ratings;                  //SQLben JOIN (food->rating)
    private int restaurantID;                   //SQLben FOREIGN KEY.


    public Food(int id, String name, FoodCategories foodCategories, int price) {
        this.id = id;
        this.name = name;
        this.foodCategories = foodCategories;
        this.price = price;
    }

    public Food(String name, FoodCategories foodCategories, int price) {
        this.name = name;
        this.foodCategories = foodCategories;
        this.price = price;
    }

    public double foodRatingsAvg(List<Integer> ratings){
        double avg=0;
        for (Integer rating : ratings) {
            avg+=rating;
        }
        avg=avg/ ratings.size();
        return avg;
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

    public FoodCategories getFoodCategories() {
        return foodCategories;
    }

    public void setFoodCategories(FoodCategories foodCategories) {
        this.foodCategories = foodCategories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }
}
