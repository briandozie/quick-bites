package curtin.edu.my.quickbites;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import curtin.edu.my.quickbites.RestaurantSchema.RestaurantTable;
import curtin.edu.my.quickbites.FoodSchema.FoodTable;
import curtin.edu.my.quickbites.AccountSchema.AccountTable;
import curtin.edu.my.quickbites.CartSchema.CartTable;
import curtin.edu.my.quickbites.OrderSchema.OrderTable;

public class QuickBitesDbModel implements Serializable
{
    private List<Restaurant> restaurantList;
    private List<Account> accountList;
    private List<Food> foodList;
    private List<CartDB> cartList;
    private List<Order> orderList;
    private SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new QuickBitesDbHelper(
                context.getApplicationContext()).getWritableDatabase();

        // read database contents into list
        readAllRestaurantFromDB();
        readAllFoodFromDB();
        readAllAccountFromDB();
        readAllCartFromDB();
        readAllOrderFromDB();
    }

    // accessors
    // get a list of all restaurants
    public ArrayList<Restaurant> getAllRestaurant()
    {
        return new ArrayList<Restaurant>(restaurantList);
    }

    public ArrayList<Order> getAllOrder() { return new ArrayList<Order>(orderList); }

    public ArrayList<Account> getAllAccount()
    {
        return new ArrayList<Account>(accountList);
    }

    public ArrayList<Food> getAllFood()
    {
        return new ArrayList<Food>(foodList);
    }

    public ArrayList<Order> getOrderListForUser(String username)
    {
        ArrayList<Order> result = new ArrayList<>();
        for(Order order: orderList)
        {
            if(order.getUser().equals(username))
            {
                result.add(order);
            }
        }
        return result;
    }

    // get a list of food for a specific restaurant
    public ArrayList<Food> getFoodForRestaurant(int restId)
    {
        Food currFood;
        ArrayList<Food> restaurantFoodList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(FoodTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                currFood = cursor.getFood();
                if(currFood.getRestId() == restId)
                {
                    restaurantFoodList.add(currFood);
                }
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }

        return restaurantFoodList;
    }

    public Account getLoggedInUser()
    {
        for(Account acc: accountList)
        {
            if (acc.getLogin() == Account.TRUE) {
                return acc;
            }
        }
        return null;
    }

    public Cart getCart(int cartId)
    {
        for(CartDB cartDB: cartList)
        {
            if(cartDB.getCartId() == cartId)
            {
                return cartDB.getCart();
            }
        }
        return null;
    }

    public void addOrder(Order order)
    {
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();

        cv.put(OrderTable.Cols.ORDERID, order.getOrderId());
        cv.put(OrderTable.Cols.RESTID, order.getRestId());
        cv.put(OrderTable.Cols.ORDERDATE, order.getDate());
        cv.put(OrderTable.Cols.ORDERTIME, order.getTime());
        cv.put(OrderTable.Cols.TOTAL, order.getTotal());
        cv.put(OrderTable.Cols.CONTENT, gson.toJson(order.getFoodMap()));
        cv.put(OrderTable.Cols.USER, order.getUser());
        db.insert(OrderTable.NAME, null, cv);
    }

    public void addAccount(Account account)
    {
        ContentValues cv = new ContentValues();
        cv.put(AccountTable.Cols.USERNAME, account.getUsername());
        cv.put(AccountTable.Cols.PASSWORD, account.getPassword());
        cv.put(AccountTable.Cols.CARTID, account.getCartId());
        cv.put(AccountTable.Cols.LOGIN, account.getLogin());
        db.insert(AccountTable.NAME, null, cv);

        addCart(account.getCartId(), new Cart());
    }

    public void updateLogin(Account account)
    {
        ContentValues cv = new ContentValues();
        cv.put(AccountTable.Cols.USERNAME, account.getUsername());
        cv.put(AccountTable.Cols.PASSWORD, account.getPassword());
        cv.put(AccountTable.Cols.CARTID, account.getCartId());
        cv.put(AccountTable.Cols.LOGIN, account.getLogin());

        String[] whereValue = { String.valueOf(account.getUsername()) };
        db.update(AccountTable.NAME, cv,
                AccountTable.Cols.USERNAME + " = ?", whereValue);
    }

    public void updateCart(int cartId, Cart cart)
    {
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();

        cv.put(CartTable.Cols.CARTID, cartId);
        cv.put(CartTable.Cols.CARTCONTENT, gson.toJson(cart));

        String[] whereValue = { String.valueOf(cartId) };
        db.update(CartTable.NAME, cv,
                CartTable.Cols.CARTID + " = ?", whereValue);
    }

    public void addCart(int cartId, Cart cart)
    {
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();

        cv.put(CartTable.Cols.CARTID, cartId);
        cv.put(CartTable.Cols.CARTCONTENT, gson.toJson(cart));
        db.insert(CartTable.NAME, null, cv);
    }

    public void removeCart(int cartId)
    {
        String[] whereValue = { String.valueOf(cartId) };
        db.delete(CartTable.NAME,
                CartTable.Cols.CARTID + " = ?", whereValue);
    }

    private void readAllRestaurantFromDB()
    {
        restaurantList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(RestaurantTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                restaurantList.add(cursor.getRestaurant());
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }
    }

    private void readAllAccountFromDB()
    {
        accountList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(AccountTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                accountList.add(cursor.getAccount());
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }
    }

    private void readAllCartFromDB()
    {
        cartList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(CartTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                cartList.add(cursor.getCart());
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }
    }

    private void readAllOrderFromDB()
    {
        orderList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(OrderTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                orderList.add(cursor.getOrder());
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }
    }

    private void readAllFoodFromDB()
    {
        foodList = new ArrayList<>();
        QuickBitesDbCursor cursor = new QuickBitesDbCursor(
                db.query(FoodTable.NAME, null, null, null, null, null, null));

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                foodList.add(cursor.getFood());
                cursor.moveToNext();
            }
        }
        finally { cursor.close(); }
    }
}
