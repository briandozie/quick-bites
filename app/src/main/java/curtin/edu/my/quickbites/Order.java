package curtin.edu.my.quickbites;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class Order implements Serializable {
    private int orderId;
    private int restId;
    private String date;
    private String time;
    private double total;
    private FoodMap foodMap;
    private String user;

    public Order(int orderId, int restId, String date, String time, double total,
                 FoodMap foodMap, String user)
    {
        this.orderId = orderId;
        this.restId = restId;
        this.time = time;
        this.total = total;
        this.foodMap = foodMap;
        this.date = date;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getRestId() {
        return restId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public FoodMap getFoodMap() {
        return foodMap;
    }

    public String getUser() {
        return user;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFoodMap(FoodMap foodMap) {
        this.foodMap = foodMap;
    }
}
