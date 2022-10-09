package curtin.edu.my.quickbites;

import java.util.AbstractMap;
import java.util.HashMap;

public class FoodMap
{
    private HashMap<Integer, Integer> foodMap;

    public FoodMap(HashMap<Integer, Integer> foodMap)
    {
        this.foodMap = foodMap;
    }

    public HashMap<Integer, Integer> getFoodMap() {
        return foodMap;
    }

    public void setFoodMap(HashMap<Integer, Integer> foodMap) {
        this.foodMap = foodMap;
    }
}
