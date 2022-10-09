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

import org.w3c.dom.Text;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {

    private class CheckoutAdapter extends RecyclerView.Adapter<CheckoutFragment.CheckoutViewHolder>
    {
        private List<Food> data;

        public CheckoutAdapter(List<Food> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(CheckoutFragment.CheckoutViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public CheckoutFragment.CheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new CheckoutFragment.CheckoutViewHolder(li, parent);
        }
    }

    private class CheckoutViewHolder extends RecyclerView.ViewHolder
    {
        private TextView fName;
        private TextView serving;
        private TextView cost;
        private ImageView fImage;

        public CheckoutViewHolder(LayoutInflater li, ViewGroup parent)
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
            int quantity = foodCartMap.get(food.getFoodId()).getValue();

            fName.setText(food.getName());
            serving.setText(String.valueOf(quantity) + " serving(s)");
            cost.setText(String.format("RM %.2f", foodCartMap.get(food.getFoodId()).getKey() * quantity));
            fImage.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                    "drawable", getActivity().getPackageName()));
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESTORDER = "restOrder";
    private static final String REST = "restaurant";
    private static final String FLIST = "foodList";
    private static final String FCMAP = "foodCartMap";

    // TODO: Rename and change types of parameters
    private Order order;
    private Restaurant restaurant;
    private ArrayList<Food> foodList;
    private ArrayList<Food> orderFoodList;
    private HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CheckoutFragment newInstance(Order order, Restaurant restaurant, ArrayList<Food> foodList,
                                                      HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESTORDER, order);
        args.putSerializable(REST, restaurant);
        args.putSerializable(FLIST, foodList);
        args.putSerializable(FCMAP, foodCartMap);
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
            this.foodCartMap = (HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>>)
                    getArguments().getSerializable(FCMAP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
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
        CheckoutFragment.CheckoutAdapter adapter = new CheckoutFragment.CheckoutAdapter(orderFoodList);
        rv.setAdapter(adapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CartActivity) getActivity()).backToHome();
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

    private ArrayList<Food> getOrderFoodList()
    {
        ArrayList<Food> result = new ArrayList<>();
        for(HashMap.Entry<Integer, AbstractMap.SimpleEntry<Double, Integer>> entry : foodCartMap.entrySet())
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