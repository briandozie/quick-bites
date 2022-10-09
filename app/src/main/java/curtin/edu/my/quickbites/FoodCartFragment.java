package curtin.edu.my.quickbites;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodCartFragment extends Fragment {

    public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartFragment.FoodCartViewHolder>
    {
        private List<Food> data;

        public FoodCartAdapter(List<Food> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(FoodCartFragment.FoodCartViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public FoodCartFragment.FoodCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new FoodCartFragment.FoodCartViewHolder(li, parent, this);
        }



        public void removeItem(int position)
        {
            itemRemoved = true;
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size());

        }
    }

    private class FoodCartViewHolder extends RecyclerView.ViewHolder
    {
        private TextView fName;
        private TextView serving;
        private ImageView fImage;
        private TextView price;
        private Food fFood;

        private FoodCartViewHolder(LayoutInflater li, ViewGroup parent, FoodCartFragment.FoodCartAdapter adapter)
        {
            super(li.inflate(R.layout.cart_food_selection, parent, false));

            // get UI element references
            fName = (TextView) itemView.findViewById(R.id.fName);
            serving = (TextView) itemView.findViewById(R.id.serving);
            price = (TextView) itemView.findViewById(R.id.fPrice);
            fImage = (ImageView) itemView.findViewById(R.id.fImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterPosition = getBindingAdapterPosition();
                    cart = ((CartActivity) getActivity()).showDialog(restaurant, fFood, cart,
                            foodCartMap.get(fFood.getFoodId()).getValue(), foodCartMap);
                }
            });
        }

        public void bind(Food inFood)
        {
            food = inFood;
            fFood = inFood;
            fName.setText(food.getName());
            serving.setText(foodCartMap.get(food.getFoodId()).getValue() + " serving(s)");
            price.setText(String.format("RM %.2f", (foodCartMap.get(food.getFoodId()).getKey()) *
                    foodCartMap.get(food.getFoodId()).getValue()));
            fImage.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                    "drawable", getActivity().getPackageName()));
        }
    }

    private static final String RESTAURANT = "restaurant";
    private static final String FOODLIST = "foodList";
    private static final String CART = "cart";
    private static final String ORDER = "order";

    private Restaurant restaurant;
    private ArrayList<Food> foodList;
    private ArrayList<Food> foodCartList;
    private Cart cart;
    private Food food;
    private int adapterPosition;
    private FoodCartFragment.FoodCartAdapter adapter;
    private HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap;
    private boolean itemRemoved;

    public FoodCartFragment() {
        // Required empty public constructor
    }

    public static FoodCartFragment newInstance(Restaurant restaurant, ArrayList<Food> foodList, Cart cart) {
        FoodCartFragment fragment = new FoodCartFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESTAURANT, restaurant);
        args.putSerializable(FOODLIST, foodList);
        args.putSerializable(CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.restaurant = (Restaurant) getArguments().getSerializable(RESTAURANT);
            this.foodList = (ArrayList<Food>) getArguments().getSerializable(FOODLIST);
            this.cart = (Cart) getArguments().getSerializable(CART);
            setupFoodCartListAndMap();
            itemRemoved = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_cart, container, false);

        QuickBitesDbModel db = new QuickBitesDbModel();
        db.load(((CartActivity) getActivity()).getApplicationContext());
        TextView subTitle = getActivity().findViewById(R.id.subTitle);
        TextView totalPrice = getActivity().findViewById(R.id.totalPrice);
        subTitle.setText(String.format("%s - %s", restaurant.getName(), restaurant.getLocation()));
        totalPrice.setText(String.format("RM %.2f", calculateTotalPrice()));

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewFoodCart);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FoodCartFragment.FoodCartAdapter(foodCartList);
        rv.setAdapter(adapter);

        Button checkoutBtn = (Button) ((CartActivity) getActivity()).findViewById(R.id.checkoutBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account loggedInUser = ((CartActivity) getActivity()).getUserLoggedIn();
                if(loggedInUser != null)
                {
                    //Bundle result = new Bundle();
                    Random rnd = new Random();
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    SimpleDateFormat df2 = new SimpleDateFormat("kk:mm", Locale.getDefault());
                    String orderDate = (df.format(date));
                    String orderTime = (df2.format(date));

                    int orderId = 100000 + rnd.nextInt(900000);
                    Order order = new Order(orderId, restaurant.getId(), orderDate, orderTime, calculateTotalPrice(), new FoodMap(generateFoodMap()), loggedInUser.getUsername());
                    ((CartActivity) getActivity()).switchToCheckoutFragment(order, restaurant, foodCartMap);
                }
                else
                {
                    ((CartActivity) getActivity()).startAccountActivity();
                }

            }
        });
        return view;
    }

    private String removeExtension(String file)
    {
        String result;
        int position;

        position = file.lastIndexOf('.');
        if(position >= 0)
        {
            result = file.substring(0, position);
        }
        else
        {
            result = file;
        }
        return result;
    }

    private void setupFoodCartListAndMap()
    {
        foodCartList = new ArrayList<>();
        foodCartMap = new HashMap<>();

        for(Food food: foodList)
        {
            if(!foodCartMap.containsKey(food.getFoodId()))
            {
                foodCartMap.put(food.getFoodId(), new AbstractMap.SimpleEntry<>(food.getCost(), 1));
                foodCartList.add(food);
            }
            else
            {
                int quantity = foodCartMap.get(food.getFoodId()).getValue();
                foodCartMap.get(food.getFoodId()).setValue(quantity + 1);
            }
        }
    }

    private double calculateTotalPrice()
    {
        double total = 0;
        for(HashMap.Entry<Integer, AbstractMap.SimpleEntry<Double, Integer>> entry : foodCartMap.entrySet())
        {
            total += (entry.getValue().getKey()) * (entry.getValue().getValue());
        }
        return total;
    }

    public void updateUI()
    {
        if(foodCartMap.get(food.getFoodId()).getValue() == 0)
        {
            foodCartMap.remove(food.getFoodId());
            adapter.removeItem(adapterPosition);
        }
        adapter.notifyDataSetChanged();
        TextView totalPrice = getActivity().findViewById(R.id.totalPrice);
        totalPrice.setText(String.format("RM %.2f", calculateTotalPrice()));

        if(foodCartMap.isEmpty())
        {
            ((CartActivity) getActivity()).switchToRestaurantCartFragment(cart);
        }
    }

    public HashMap<Integer, Integer> generateFoodMap()
    {
        HashMap<Integer, Integer> foodMap = new HashMap<>();

        for(HashMap.Entry <Integer, AbstractMap.SimpleEntry<Double, Integer>> entry: foodCartMap.entrySet())
        {
            foodMap.put(entry.getKey(), entry.getValue().getValue());
        }
        return foodMap;
    }
}