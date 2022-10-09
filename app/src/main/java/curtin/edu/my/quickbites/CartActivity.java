package curtin.edu.my.quickbites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity implements CartEditDialogFragment.CartEditDialogListener {

    private static final String CART = "curtin.edu.my.quickbites.cart";
    private static final int REQUEST_CODE_CART = 5;
    private boolean editing = false;
    private RestaurantCartFragment resCartFrag;
    private Cart cart;
    private FragmentManager fm;
    private FoodCartFragment fcFrag;
    private TextView total;
    private TextView totalPrice;
    private Button checkoutBtn;
    private View line;
    private TextView subTitle;
    private QuickBitesDbModel db;
    private Account loggedInUser;
    private TextView pageTitle;
    private Button editBtn;
    private CheckoutFragment coFrag;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = new QuickBitesDbModel();
        db.load(getApplicationContext());

        loggedInUser = db.getLoggedInUser();

        editBtn = (Button) findViewById(R.id.editBtn);
        checkoutBtn = (Button) findViewById(R.id.checkoutBtn);
        total = (TextView) findViewById(R.id.total);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        line = (View) findViewById(R.id.line);
        subTitle = (TextView) findViewById(R.id.subTitle);
        pageTitle = (TextView) findViewById(R.id.pageTitle);

        Intent intent = getIntent();
        cart = (Cart) intent.getSerializableExtra("curtin.edu.my.quickbites.cart");

        Intent returnData = new Intent();
        returnData.putExtra(CART, cart);
        setResult(RESULT_OK, returnData);

        fm = getSupportFragmentManager();
        fcFrag = (FoodCartFragment) fm.findFragmentById(R.id.list_container_2);
        resCartFrag = (RestaurantCartFragment) fm.findFragmentById(R.id.list_container);
        coFrag = (CheckoutFragment) fm.findFragmentById(R.id.checkout_container);

        if(!(fm.findFragmentById(R.id.checkout_container) instanceof CheckoutFragment))
        {
            if(resCartFrag == null &&
                    !(fm.findFragmentById(R.id.list_container_2) instanceof FoodCartFragment))
            {
                resCartFrag = RestaurantCartFragment.newInstance(cart);
                fm.beginTransaction().add(R.id.list_container, resCartFrag).commit();
            }

            if(fm.findFragmentById(R.id.list_container_2) instanceof FoodCartFragment)
            {
                line.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                checkoutBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            pageTitle.setVisibility(View.INVISIBLE);
            editBtn.setVisibility(View.INVISIBLE);
        }


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editing)
                {
                    editBtn.setBackgroundColor(Color.RED);
                    editBtn.setText("Done");
                    editing = true;
                    resCartFrag.showRemoveButton();
                }
                else
                {
                    editBtn.setBackgroundColor(Color.parseColor("#5b39c6"));
                    editBtn.setText("Edit");
                    editing = false;
                    resCartFrag.hideRemoveButton();
                }
            }
        });
    }

    public Cart removeRestaurantFromCart(int restID)
    {
        cart.removeRestaurant(restID);
        if(loggedInUser != null)
        {
            // update db
            db.updateCart(loggedInUser.getCartId(), cart);
        }
        else
        {
            db.updateCart(1234567, cart);
        }
        if(cart.isEmpty())
        {
            findViewById(R.id.noItemBox).setVisibility(View.VISIBLE);
            findViewById(R.id.cartPic).setVisibility(View.VISIBLE);
        }
        return cart;
    }

    public void addToCart(Restaurant restaurant, Food food, int quantity)
    {
        cart.addFood(restaurant, food, quantity);
        resCartFrag.updateUI();
        if(loggedInUser != null)
        {
            db.updateCart(loggedInUser.getCartId(), cart);
        }
        else
        {
            db.updateCart(1234567, cart);
        }
        Snackbar.make(findViewById(R.id.list_container), String.format("Added %d item(s) to cart", quantity), Snackbar.LENGTH_SHORT)
                .show();
    }

    public void removeFromCart(Restaurant restaurant, Food food, int quantity)
    {
        cart.removeFood(restaurant, food, quantity);
        resCartFrag.updateUI();
        if(loggedInUser != null)
        {
            db.updateCart(loggedInUser.getCartId(), cart);
        }
        else
        {
            db.updateCart(1234567, cart);
        }
        Snackbar.make(findViewById(R.id.list_container), String.format("Removed %d item(s) to cart", quantity), Snackbar.LENGTH_SHORT)
                .show();
    }

    public Cart showDialog(Restaurant restaurant, Food food, Cart cart, int quantity,
                           HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap)
    {
        CartEditDialogFragment dialog = CartEditDialogFragment.newInstance(restaurant ,food, cart, quantity, foodCartMap);
        dialog.show(fm, "edit_cart");
        return cart;
    }

    public static Intent getIntent(Context c, Cart cart)
    {
        Intent intent = new Intent(c, CartActivity.class);
        intent.putExtra(CART, cart);
        return intent;
    }

    public static Serializable getCart(Intent intent)
    {
        return intent.getSerializableExtra(CART);
    }

    public void switchToFoodCartFragment(Restaurant restaurant)
    {
        if(editing)
        {
            Snackbar.make(findViewById(R.id.list_container), "Please finish editing first!", Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            fcFrag = FoodCartFragment.newInstance(restaurant, cart.getFoodListForRestaurant(restaurant.getId()), cart);
            fm.beginTransaction().hide(resCartFrag).commit();
            fm.beginTransaction().replace(R.id.list_container_2, fcFrag).addToBackStack("food_cart_frag").commit();

            line.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.VISIBLE);
            checkoutBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void switchToRestaurantCartFragment(Cart cart)
    {
        line.setVisibility(View.INVISIBLE);
        total.setVisibility(View.INVISIBLE);
        totalPrice.setVisibility(View.INVISIBLE);
        checkoutBtn.setVisibility(View.INVISIBLE);
        editBtn.setVisibility(View.VISIBLE);

        resCartFrag = RestaurantCartFragment.newInstance(cart);
        fm.beginTransaction().remove(fcFrag).commit();
        fm.beginTransaction().add(R.id.list_container, resCartFrag).commit();
    }


    public void switchToCheckoutFragment(Order order, Restaurant restaurant, HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap)
    {
        this.order = order;
        db.addOrder(order);
        cart.removeRestaurant(order.getRestId());
        db.updateCart(loggedInUser.getCartId(), cart);

        line.setVisibility(View.INVISIBLE);
        total.setVisibility(View.INVISIBLE);
        totalPrice.setVisibility(View.INVISIBLE);
        checkoutBtn.setVisibility(View.INVISIBLE);
        editBtn.setVisibility(View.INVISIBLE);
        pageTitle.setVisibility(View.INVISIBLE);
        subTitle.setVisibility(View.INVISIBLE);

        coFrag = CheckoutFragment.newInstance(order, restaurant, db.getFoodForRestaurant(restaurant.getId()), foodCartMap);
        fm.beginTransaction().remove(fcFrag).commit();
        fm.beginTransaction().remove(resCartFrag).commit();
        //fm.beginTransaction().replace(R.id.checkout_container, coFrag).addToBackStack("checkout_frag").commit();
        fm.beginTransaction().replace(R.id.checkout_container, coFrag).commit();

        Snackbar.make(findViewById(R.id.checkout_container), "Checkout successful", Snackbar.LENGTH_SHORT).show();
    }

    public void backToHome()
    {
        finish();
    }

    public Account getUserLoggedIn()
    {
        return loggedInUser;
    }

    public void startAccountActivity()
    {
        startActivityForResult(AccountActivity.getIntent(CartActivity.this, "login"), REQUEST_CODE_CART);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!(fm.findFragmentById(R.id.list_container) instanceof RestaurantCartFragment)
                && fm.getBackStackEntryCount() == 0)
        {
            finish();
        }
        else
        {
           // if (!(fm.findFragmentById(R.id.list_container) instanceof RestaurantCartFragment)) {
                subTitle.setText("Restaurants");
                editBtn.setVisibility(View.VISIBLE);
                //FoodCartFragment frag = (FoodCartFragment) fm.findFragmentById(R.id.list_container_2);

                fm.beginTransaction().show(resCartFrag).commit();
                //fm.beginTransaction().replace(R.id.list_container, resCartFrag).commit();
           // }

            line.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            totalPrice.setVisibility(View.INVISIBLE);
            checkoutBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CART)
        {
            loggedInUser = AccountActivity.getAccount(data);
        }
    }

    /*public void setOrder(Order order)
    {
        db.addOrder(order);
        cart.removeRestaurant(order.getRestId());
        db.updateCart(loggedInUser.getCartId(), cart);
        finish();
    }*/

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        FoodCartFragment frag = (FoodCartFragment) fm.findFragmentById(R.id.list_container_2);
        frag.updateUI();
    }
}