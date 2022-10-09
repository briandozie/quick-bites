package curtin.edu.my.quickbites;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationBarView bottomNav;
    private NavigationView navView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayList<Restaurant> restaurantList;
    private ArrayList<Food> foodList;
    private SpecialMenuFragment smFrag;
    private RestaurantFragment rFrag;
    private RestaurantMenuFragment rmFrag;
    private ArrayList<Account> accountList;
    private FragmentManager fm;
    QuickBitesDbModel db;
    private Cart cart;
    private static final int REQUEST_CODE_CART = 0;
    private static final int REQUEST_CODE_LOGIN = 1;
    private boolean login;
    private Account loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database stuff
        db = new QuickBitesDbModel();
        db.load(getApplicationContext());

        restaurantList = db.getAllRestaurant();
        foodList = db.getAllFood();
        accountList = db.getAllAccount();

        FloatingActionButton cartIcon = findViewById(R.id.cart);
        drawerLayout = findViewById(R.id.qbDrawerLayout);
        bottomNav = findViewById(R.id.bottom_navigation);
        navView = (NavigationView) findViewById(R.id.nav_view);

        loggedInUser = db.getLoggedInUser();
        if(loggedInUser != null)
        {
            login = true;

            // clear default cart
            if(db.getCart(1234567) != null)
            {
                db.removeCart(1234567);
            }

            // load user cart
            cart = db.getCart(loggedInUser.getCartId());

            navView.getMenu().clear();
            navView.inflateMenu(R.menu.nav_menu_logged_in);
            Snackbar.make(findViewById(R.id.list_container), String.format("Welcome back, %s ! ",
                            loggedInUser.getUsername()), Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            login = false;
            if(db.getCart(1234567) == null)
            {
                cart = new Cart();
                db.addCart(1234567, cart);
            }
            else
            {
                cart = db.getCart(1234567);
            }

            navView.getMenu().clear();
            navView.inflateMenu(R.menu.nav_menu_logged_out);
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_login:
                        startActivityForResult(AccountActivity.getIntent(MainActivity.this, "login"), REQUEST_CODE_LOGIN);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        Snackbar.make(findViewById(R.id.list_container), "Logged out", Snackbar.LENGTH_SHORT).show();
                        loggedInUser.setLogin(Account.FALSE);
                        db.updateLogin(loggedInUser);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_orderHistory:
                        startActivity(AccountActivity.getIntent(MainActivity.this, "order"));
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

        // add listener to open and close navigation drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();
        if(fm.findFragmentById(R.id.list_container) == null)
        {
            smFrag = (SpecialMenuFragment) fm.findFragmentById(R.id.list_container);
        }
        if((fm.findFragmentById(R.id.list_container) instanceof RestaurantFragment))
        {
            rFrag = (RestaurantFragment) fm.findFragmentById(R.id.list_container);
        }
        else if((fm.findFragmentById(R.id.list_container) instanceof SpecialMenuFragment))
        {
            smFrag = (SpecialMenuFragment) fm.findFragmentById(R.id.list_container);
        }
        else if((fm.findFragmentById(R.id.list_container) instanceof RestaurantMenuFragment))
        {
            rmFrag = (RestaurantMenuFragment) fm.findFragmentById(R.id.list_container);
        }

        if(smFrag == null)
        {
            if(!((fm.findFragmentById(R.id.list_container) instanceof RestaurantFragment))
                    && !((fm.findFragmentById(R.id.list_container) instanceof RestaurantMenuFragment)))
            {
                smFrag = SpecialMenuFragment.newInstance(foodList, restaurantList);
                fm.beginTransaction().add(R.id.list_container, smFrag).commit();
            }
        }
        if(rFrag == null)
        {
            rFrag = RestaurantFragment.newInstance(restaurantList, foodList);
        }

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CartActivity.getIntent(MainActivity.this, cart), REQUEST_CODE_CART);
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.menu:
                        if((fm.findFragmentById(R.id.list_container) instanceof RestaurantFragment))
                        {
                            fm.beginTransaction().remove(rFrag).commit();
                            smFrag = SpecialMenuFragment.newInstance(foodList, restaurantList);
                            fm.beginTransaction().add(R.id.list_container, smFrag).commit();
                        }
                        else if((fm.findFragmentById(R.id.list_container) instanceof RestaurantMenuFragment))
                        {
                            fm.beginTransaction().remove(rmFrag).commit();
                            smFrag = SpecialMenuFragment.newInstance(foodList, restaurantList);
                            fm.beginTransaction().add(R.id.list_container, smFrag).commit();
                        }
                        return true;
                    case R.id.restaurant:
                        if((fm.findFragmentById(R.id.list_container) instanceof SpecialMenuFragment))
                        {
                            fm.beginTransaction().remove(smFrag).commit();
                            fm.beginTransaction().add(R.id.list_container, rFrag).commit();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void switchToRestaurantMenuFragment(Restaurant restaurant)
    {
        rmFrag = RestaurantMenuFragment.newInstance(foodList, restaurant);
        fm.beginTransaction().replace(R.id.list_container, rmFrag).addToBackStack("tag").commit();
    }

    public void showDialog(Restaurant restaurant, Food food)
    {
        CartDialogFragment dialog = CartDialogFragment.newInstance(restaurant ,food, cart);
        dialog.show(fm, "add_to_cart");
    }

    // TODO
    public void addToCart(Restaurant restaurant, Food food, int quantity)
    {
        cart.addFood(restaurant, food, quantity);
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

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CART)
        {
            //this.cart = (Cart) CartActivity.getCart(data);
            //if(resultCode == RESULT_OK)
            {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }
        else if(requestCode == REQUEST_CODE_LOGIN)
        {
            Snackbar.make(findViewById(R.id.list_container), "Log in successful",
                    Snackbar.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }
}