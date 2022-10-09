package curtin.edu.my.quickbites;

import java.io.Serializable;

public class Food implements Serializable
{
    private int foodId;
    private int restId;
    private String name;
    private double cost;
    private String desc;
    private String image;

    public Food(int foodId, int restId, String name,
                double cost, String desc, String image)
    {
        this.foodId = foodId;
        this.restId = restId;
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.image = image;
    }

    // accessors
    public int getFoodId() {
        return foodId;
    }

    public int getRestId() {
        return restId;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    // mutators
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
