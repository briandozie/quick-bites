package curtin.edu.my.quickbites;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantOrderFragment extends Fragment {

    private class RestaurantOrderAdapter extends RecyclerView.Adapter<RestaurantOrderFragment.RestaurantOrderViewHolder>
    {
        private List<Food> data;

        public RestaurantOrderAdapter(List<Food> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(RestaurantOrderFragment.RestaurantOrderViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public RestaurantOrderFragment.RestaurantOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new RestaurantOrderFragment.RestaurantOrderViewHolder(li, parent);
        }
    }

    private class RestaurantOrderViewHolder extends RecyclerView.ViewHolder
    {
        private TextView fName;
        private TextView serving;
        private TextView cost;
        private ImageView fImage;

        public RestaurantOrderViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.restaurant_order_list_selection, parent, false));

            // get UI element references
            fName = (TextView) itemView.findViewById(R.id.foodNameBox);
            cost = (TextView) itemView.findViewById(R.id.costBox);
            serving = (TextView) itemView.findViewById(R.id.servingBox);
            fImage = (ImageView) itemView.findViewById(R.id.foodImageBox);
        }

        public void bind(Food food)
        {
            int quantity = order.getFoodMap().getFoodMap().get(food.getFoodId());

            fName.setText(food.getName());
            serving.setText(String.valueOf(quantity) + " serving(s)");
            cost.setText(String.format("RM %.2f", food.getCost() * quantity));
            fImage.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                    "drawable", getActivity().getPackageName()));
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESTORDER = "restOrder";
    private static final String REST = "restaurant";
    private static final String FLIST = "foodList";

    // TODO: Rename and change types of parameters
    private Order order;
    private Restaurant restaurant;
    private ArrayList<Food> foodList;
    private ArrayList<Food> orderFoodList;

    public RestaurantOrderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantOrderFragment newInstance(Order order, Restaurant restaurant, ArrayList<Food> foodList) {
        RestaurantOrderFragment fragment = new RestaurantOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESTORDER, order);
        args.putSerializable(REST, restaurant);
        args.putSerializable(FLIST, foodList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(RESTORDER);
            restaurant = (Restaurant) getArguments().getSerializable(REST);
            foodList = (ArrayList<Food>) getArguments().getSerializable(FLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_order, container, false);
        TextView subtitle = view.findViewById(R.id.subTitle);
        TextView orderID = view.findViewById(R.id.orderID);
        TextView timeBox = view.findViewById(R.id.orderTime);
        TextView dateBox = view.findViewById(R.id.orderDate);
        TextView totalBox = view.findViewById(R.id.totalCost);
        Button done = view.findViewById(R.id.doneBtn);

        subtitle.setText(String.format("%s - %s", restaurant.getName(), restaurant.getLocation()));
        orderID.setText(String.format("Order ID: %d", order.getOrderId()));
        timeBox.setText(String.format("Time: %s", order.getTime()));
        dateBox.setText(String.format("Date: %s", order.getDate()));
        totalBox.setText(String.format("Total: RM %.2f", order.getTotal()));

        orderFoodList = getOrderFoodList();

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewRestaurantOrderHistory);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RestaurantOrderFragment.RestaurantOrderAdapter adapter = new RestaurantOrderFragment.RestaurantOrderAdapter(orderFoodList);
        rv.setAdapter(adapter);

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

    private ArrayList<Food> getOrderFoodList()
    {
        ArrayList<Food> result = new ArrayList<>();
        for(HashMap.Entry<Integer, Integer> entry : order.getFoodMap().getFoodMap().entrySet())
        {
            for(Food food: foodList)
            {
                if(food.getFoodId() == (entry.getKey()))
                {
                    result.add(food);
                    break;
                }
            }
        }
        return result;
    }
}