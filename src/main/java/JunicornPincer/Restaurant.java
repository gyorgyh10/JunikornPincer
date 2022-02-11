package JunicornPincer;

import java.util.List;

public class Restaurant {
    private int id;
    private String name;
    private Address address;
    private List<Food> foodList;
    private boolean canDeliver;
    private List<Integer> ratings;

    public double restaurantAvg(List<Integer> ratings){
        double avg=0;
        for (Integer rating : ratings) {
            avg+=rating;
        }
        avg=avg/ ratings.size();
        return avg;
    }
}


