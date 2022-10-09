package curtin.edu.my.quickbites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    private static final String ACCOUNTLOGGED = "curtin.edu.my.accountLogged";
    private static final String OPERATION = "curtin.edu.my.operation";
    private FragmentManager fm;
    private LoginFragment lFrag;
    private RegisterFragment rFrag;
    private OrderHistoryFragment ohFrag;
    private ArrayList<Account> accountList;
    private RestaurantOrderFragment roFrag;
    private String frag;
    private View line;
    private String operation;
    private boolean isTablet;
    private QuickBitesDbModel db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        db = new QuickBitesDbModel();
        db.load(getApplicationContext());

        Intent intent = getIntent();
        operation = intent.getStringExtra("curtin.edu.my.operation");

        accountList = db.getAllAccount();
        fm = getSupportFragmentManager();

        // determine if device is phone or tablet
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        double yInch = metrics.heightPixels/metrics.ydpi;
        double xInch = metrics.widthPixels/metrics.xdpi;
        double diagonalInch = Math.sqrt(xInch * xInch + yInch * yInch);
        if(diagonalInch >= 7)
        {
            isTablet = true;
            line = findViewById(R.id.line);
            line.setVisibility(View.INVISIBLE);
        }

        if(fm.findFragmentById(R.id.fragment_container)  == null)
        {
            lFrag = (LoginFragment) fm.findFragmentById(R.id.fragment_container);
        }
        else
        {
            if((fm.findFragmentById(R.id.fragment_container) instanceof LoginFragment))
            {
                lFrag = (LoginFragment) fm.findFragmentById(R.id.fragment_container);
            }
            else if((fm.findFragmentById(R.id.fragment_container) instanceof RegisterFragment))
            {
                rFrag = (RegisterFragment) fm.findFragmentById(R.id.fragment_container);
            }

            if(isTablet) // is tablet
            {
                ohFrag = (OrderHistoryFragment) fm.findFragmentById(R.id.orderHistory_container);
                roFrag = (RestaurantOrderFragment) fm.findFragmentById(R.id.restaurantOrder_container);
            }
            else
            {
                if((fm.findFragmentById(R.id.fragment_container) instanceof OrderHistoryFragment))
                {
                    ohFrag = (OrderHistoryFragment) fm.findFragmentById(R.id.fragment_container);
                }
                else if((fm.findFragmentById(R.id.fragment_container) instanceof RestaurantOrderFragment))
                {
                    roFrag = (RestaurantOrderFragment) fm.findFragmentById(R.id.fragment_container);
                }
            }

        }

        if(operation.equals("login"))
        {
            if(lFrag == null &&
                !(fm.findFragmentById(R.id.fragment_container) instanceof RegisterFragment))
            {
                if(isTablet)
                {
                    line.setVisibility(View.INVISIBLE);
                }
                lFrag = LoginFragment.newInstance(accountList);
                fm.beginTransaction().add(R.id.fragment_container, lFrag).commit();
            }
        }
        else // order history
        {
            if(ohFrag == null)
            {
                ohFrag = OrderHistoryFragment.newInstance(db.getOrderListForUser(
                        db.getLoggedInUser().getUsername()), db.getAllRestaurant());
                if(isTablet)
                {
                    line.setVisibility(View.VISIBLE);
                    if(!(fm.findFragmentById(R.id.orderHistory_container) instanceof  OrderHistoryFragment))
                    {
                        fm.beginTransaction().add(R.id.orderHistory_container, ohFrag).commit();
                    }
                }
                else
                {
                    if(!(fm.findFragmentById(R.id.fragment_container) instanceof RestaurantOrderFragment))
                    {
                        fm.beginTransaction().add(R.id.fragment_container, ohFrag).commit();
                    }
                }
            }
        }

    }

    public static Intent getIntent(Context context, String operation)
    {
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra(OPERATION, operation);
        return intent;
    }

    public void switchToRestaurantOrderFragment(Order order, Restaurant restaurant)
    {

        roFrag = RestaurantOrderFragment.newInstance(order, restaurant, db.getFoodForRestaurant(restaurant.getId()));
        if(isTablet)
        {
            line.setVisibility(View.VISIBLE);
            fm.beginTransaction().replace(R.id.restaurantOrder_container, roFrag).commit();
        }
        else
        {
            fm.beginTransaction().replace(R.id.fragment_container, roFrag).addToBackStack("resOrderFrag").commit();
        }
    }

    public void switchToRegisterFragment()
    {
        if(isTablet)
        {
            line.setVisibility(View.INVISIBLE);
        }
        rFrag = RegisterFragment.newInstance(accountList);
        fm.beginTransaction().replace(R.id.fragment_container, rFrag).addToBackStack("registerFrag").commit();
    }

    public void addAccount(Account account)
    {
        accountList.add(account);
        db.addAccount(account);

        // back to login
        fm.beginTransaction().remove(rFrag).commit();
        lFrag = LoginFragment.newInstance(accountList);
        fm.beginTransaction().add(R.id.fragment_container, lFrag).commit();
        Snackbar.make(findViewById(R.id.fragment_container), "Account registered! Please log in", Snackbar.LENGTH_SHORT).show();
    }

    public void loginUser(Account account)
    {
        account.setLogin(Account.TRUE);
        db.updateLogin(account);

        Intent returnData = new Intent();
        returnData.putExtra(ACCOUNTLOGGED, account);
        setResult(RESULT_OK, returnData);

        finish();
    }

    public static Account getAccount(Intent intent)
    {
        return (Account) intent.getSerializableExtra(ACCOUNTLOGGED);
    }
}