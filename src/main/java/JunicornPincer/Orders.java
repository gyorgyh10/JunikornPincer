package JunicornPincer;

import java.util.List;

public class Orders {
    private int id;
    private String date;
    private List<Food> foodList;

    private int customerId;
    private int estimatedWaitingTime;


    public Orders(String date, List<Food> foodList, int customerId) {
        this.date = date;
        this.foodList = foodList;
        this.customerId = customerId;
    }

    public Orders(int id, String date, List<Food> foodList, int customerId) {
        this.id = id;
        this.date = date;
        this.foodList = foodList;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getEstimatedWaitingTime() {
        return estimatedWaitingTime;
    }

    public void setEstimatedWaitingTime(int estimatedWaitingTime) {
        this.estimatedWaitingTime = estimatedWaitingTime;
    }
}
