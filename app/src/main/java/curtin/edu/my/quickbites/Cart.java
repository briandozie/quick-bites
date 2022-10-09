package curtin.edu.my.quickbites;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable
{
    private HashMap<Integer, ArrayList<Food>> cartMap;
    private ArrayList<Restaurant> restaurantList;

    public Cart()
    {
        cartMap = new HashMap<>();
        restaurantList = new ArrayList<>();
    }

    public ArrayList<Restaurant> getRestaurantList()
    {
        return restaurantList;
    }

    public ArrayList<Food> getFoodListForRestaurant(int restId)
    {
        return cartMap.get(restId);
    }

    public int getRestaurantItemQuantity(int restId)
    {
        return cartMap.get(restId).size();
    }

    public void addFood(Restaurant restaurant, Food food, int quantity)
    {
        while(quantity > 0)
        {
            ArrayList<Food> fList;
            if(cartMap.containsKey(restaurant.getId()))
            {
                fList = cartMap.get(restaurant.getId());
            }
            else
            {
                fList = new ArrayList<>();
                restaurantList.add(restaurant);
            }
            fList.add(food);
            cartMap.put(restaurant.getId(), fList);
            quantity--;
        }
    }

    public void removeFood(Restaurant restaurant, Food food, int quantity)
    {
        int indexToDel = 0;
        ArrayList<Food> fList = cartMap.get(restaurant.getId());

        while(quantity > 0)
        {
            for(int i = 0; i < fList.size(); i++)
            {
                if(fList.get(i).getFoodId() == food.getFoodId())
                {
                    indexToDel = i;
                    break;
                }
            }
            fList.remove(indexToDel);
            cartMap.put(restaurant.getId(), fList);
            quantity--;
        }

        if(fList.isEmpty())
        {
            removeRestaurant(restaurant.getId());
        }

    }

    public void removeRestaurant(int restID)
    {
        cartMap.remove(restID);
        restaurantList.removeIf(res -> res.getId() == restID);
    }

    public boolean isEmpty()
    {
        return cartMap.isEmpty();
    }
}
