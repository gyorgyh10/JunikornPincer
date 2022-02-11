package JunicornPincer;

import java.util.List;

public class Food {
    private int id;
    private String name;
    private FoodCategories foodCategories;
    private int price;
    private List<Integer> ratings;
    private int restaurantID;


    public double foodRatingsAvg(List<Integer> ratings){
        double avg=0;
        for (Integer rating : ratings) {
            avg+=rating;
        }
        avg=avg/ ratings.size();
        return avg;
    }
}
