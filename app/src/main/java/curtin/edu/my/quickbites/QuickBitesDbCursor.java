package curtin.edu.my.quickbites;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import curtin.edu.my.quickbites.RestaurantSchema.RestaurantTable;
import curtin.edu.my.quickbites.FoodSchema.FoodTable;
import curtin.edu.my.quickbites.AccountSchema.AccountTable;
import curtin.edu.my.quickbites.CartSchema.CartTable;
import curtin.edu.my.quickbites.OrderSchema.OrderTable;

public class QuickBitesDbCursor extends CursorWrapper implements Serializable
{
    public QuickBitesDbCursor(Cursor cursor) {super(cursor);}

    public Restaurant getRestaurant()
    {
        int id;
        String name, image, location;

        id = getInt(getColumnIndex(RestaurantTable.Cols.ID));
        name = getString(getColumnIndex(RestaurantTable.Cols.NAME));
        image = getString(getColumnIndex(RestaurantTable.Cols.IMAGE));
        location = getString(getColumnIndex(RestaurantTable.Cols.LOCATION));

        return new Restaurant(id, name, image, location);
    }

    public Food getFood()
    {
        int foodId, restId;
        double cost;
        String name, desc, image;

        foodId = getInt(getColumnIndex(FoodTable.Cols.FOODID));
        restId = getInt(getColumnIndex(FoodTable.Cols.RESTID));
        name = getString(getColumnIndex(FoodTable.Cols.NAME));
        cost = getDouble(getColumnIndex(FoodTable.Cols.COST));
        desc = getString(getColumnIndex(FoodTable.Cols.DESC));
        image = getString(getColumnIndex(FoodTable.Cols.IMAGE));

        return new Food(foodId, restId, name, cost, desc, image);
    }

    public Account getAccount()
    {
        String username, password;
        int cartId, login;

        username = getString(getColumnIndex(AccountTable.Cols.USERNAME));
        password = getString(getColumnIndex(AccountTable.Cols.PASSWORD));
        cartId = getInt(getColumnIndex(AccountTable.Cols.CARTID));
        login = getInt(getColumnIndex(AccountTable.Cols.LOGIN));
        return new Account(username, password, cartId, login);
    }

    public CartDB getCart()
    {
        Gson gson = new Gson();

        int cartId = getInt(getColumnIndex(CartTable.Cols.CARTID));
        Cart cart = (Cart) gson.fromJson(getString(getColumnIndex(CartTable.Cols.CARTCONTENT)), Cart.class);
        return new CartDB(cartId, cart);
    }

    public Order getOrder()
    {
        Gson gson = new Gson();

        int orderId = getInt(getColumnIndex(OrderTable.Cols.ORDERID));
        int restId = getInt(getColumnIndex(OrderTable.Cols.RESTID));
        String date = getString(getColumnIndex(OrderTable.Cols.ORDERDATE));
        String time = getString(getColumnIndex(OrderTable.Cols.ORDERTIME));
        double total = getDouble(getColumnIndex(OrderTable.Cols.TOTAL));
        FoodMap foodMap = (FoodMap) gson.fromJson(getString(getColumnIndex(OrderTable.Cols.CONTENT)), FoodMap.class);
        String user = getString(getColumnIndex(OrderTable.Cols.USER));
        return new Order(orderId, restId, date, time, total, foodMap, user);
    }
}
