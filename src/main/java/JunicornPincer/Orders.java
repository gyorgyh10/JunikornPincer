package JunicornPincer;

import java.sql.Date;
import java.util.List;

public class Orders {
    private int id;
    private Date date;
    private List<Food> foodList;
    private Customer customer;
    private int estimatedWaitingTime;

    public Orders(int id, Date date, List<Food> foodList, Customer customer) {
        this.id = id;
        this.date = date;
        this.foodList = foodList;
        this.customer = customer;
    }

    public Orders(Date date, List<Food> foodList, Customer customer) {
        this.date = date;
        this.foodList = foodList;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getEstimatedWaitingTime() {
        return estimatedWaitingTime;
    }

    public void setEstimatedWaitingTime(int estimatedWaitingTime) {
        this.estimatedWaitingTime = estimatedWaitingTime;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", date=" + date +
                ", foodList=" + foodList +
                ", customer=" + customer +
                ", estimatedWaitingTime=" + estimatedWaitingTime +
                '}';
    }
}