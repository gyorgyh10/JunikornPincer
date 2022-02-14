package JunicornPincer;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int id;
    private String name;
    private Address address;
    private String phoneNumber;
    private List<Food> foodList;                        //SQLben ez csak egy join.(food->restaurantID-vel)
    private boolean canDeliver;
    private List<Integer> ratings = new ArrayList<>();  //SQLben rating tábla. aztán JOIN (food->rating->restaurantID-val)


    public Restaurant(String name, Address address, String phoneNumber, List<Food> foodList, boolean canDeliver) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.foodList = foodList;
        this.canDeliver = canDeliver;
    }

    public Restaurant(int id, String name, Address address, String phoneNumber, List<Food> foodList, boolean canDeliver) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.foodList = foodList;
        this.canDeliver = canDeliver;
    }

    public double restaurantAvg(List<Integer> ratings) {
        double avg = 0;
        for (Integer rating : ratings) {
            avg += rating;
        }
        avg = avg / ratings.size();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public boolean isCanDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(boolean canDeliver) {
        this.canDeliver = canDeliver;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", foodList=" + foodList +
                ", canDeliver=" + canDeliver +
                ", ratings=" + ratings +
                '}';
    }
}


