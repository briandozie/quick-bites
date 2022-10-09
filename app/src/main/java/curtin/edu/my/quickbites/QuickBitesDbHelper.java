package curtin.edu.my.quickbites;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

import curtin.edu.my.quickbites.AccountSchema.AccountTable;
import curtin.edu.my.quickbites.RestaurantSchema.RestaurantTable;
import curtin.edu.my.quickbites.FoodSchema.FoodTable;
import curtin.edu.my.quickbites.CartSchema.CartTable;
import curtin.edu.my.quickbites.OrderSchema.OrderTable;

public class QuickBitesDbHelper extends SQLiteOpenHelper implements Serializable
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "quickbites.db";

    public QuickBitesDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // create restaurant table
        db.execSQL("CREATE TABLE " + RestaurantTable.NAME + "(" +
                RestaurantTable.Cols.ID + " INTEGER, " +
                RestaurantTable.Cols.NAME + " TEXT, " +
                RestaurantTable.Cols.IMAGE + " TEXT, " +
                RestaurantTable.Cols.LOCATION + " TEXT)");

        db.execSQL("CREATE TABLE " + FoodTable.NAME + "(" +
                FoodTable.Cols.FOODID + " INTEGER, " +
                FoodTable.Cols.RESTID + " INTEGER, " +
                FoodTable.Cols.NAME + " TEXT, " +
                FoodTable.Cols.COST + " REAL, " +
                FoodTable.Cols.DESC + " TEXT, " +
                FoodTable.Cols.IMAGE + " TEXT)");

        db.execSQL("CREATE TABLE " + AccountTable.NAME + "(" +
                AccountTable.Cols.USERNAME + " TEXT, " +
                AccountTable.Cols.PASSWORD + " TEXT, " +
                AccountTable.Cols.CARTID + " INTEGER, " +
                AccountTable.Cols.LOGIN + " INTEGER)");

        db.execSQL("CREATE TABLE " + OrderTable.NAME + "(" +
                OrderTable.Cols.ORDERID + " INTEGER, " +
                OrderTable.Cols.RESTID + " INTEGER, " +
                OrderTable.Cols.ORDERDATE + " TEXT, " +
                OrderTable.Cols.ORDERTIME + " TEXT, " +
                OrderTable.Cols.TOTAL + " REAL, " +
                OrderTable.Cols.USER + " TEXT, " +
                OrderTable.Cols.CONTENT + " TEXT)");

        db.execSQL("CREATE TABLE " + CartTable.NAME + "(" +
                CartTable.Cols.CARTID + " INTEGER, " +
                CartTable.Cols.CARTCONTENT + " TEXT)");

        insertRestaurants(db);
        insertFood(db);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int v1, int v2){
        db.delete(RestaurantTable.NAME, null, null);
        db.delete(FoodTable.NAME, null, null);
        insertRestaurants(db);
        insertFood(db);
    }

    private void insertRestaurants(SQLiteDatabase db)
    {
        ArrayList<Restaurant> restaurantList =  Data.getAllRestaurant();

        for(Restaurant restaurant: restaurantList)
        {
            ContentValues cv = new ContentValues();
            cv.put(RestaurantTable.Cols.ID, restaurant.getId());
            cv.put(RestaurantTable.Cols.NAME, restaurant.getName());
            cv.put(RestaurantTable.Cols.IMAGE, restaurant.getImage());
            cv.put(RestaurantTable.Cols.LOCATION, restaurant.getLocation());
            db.insert(RestaurantTable.NAME, null, cv);
        }
    }

    private void insertFood(SQLiteDatabase db)
    {
        ArrayList<Food> foodList =  Data.getAllFood();
        for(Food food: foodList)
        {
            ContentValues cv = new ContentValues();
            cv.put(FoodTable.Cols.FOODID, food.getFoodId());
            cv.put(FoodTable.Cols.RESTID, food.getRestId());
            cv.put(FoodTable.Cols.NAME, food.getName());
            cv.put(FoodTable.Cols.COST, food.getCost());
            cv.put(FoodTable.Cols.DESC, food.getDesc());
            cv.put(FoodTable.Cols.IMAGE, food.getImage());
            db.insert(FoodTable.NAME, null, cv);
        }
    }
}
