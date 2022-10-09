package curtin.edu.my.quickbites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistoryFragment extends Fragment {

    private class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryFragment.OrderHistoryViewHolder>
    {
        private List<Order> data;

        public OrderHistoryAdapter(List<Order> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(OrderHistoryFragment.OrderHistoryViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public OrderHistoryFragment.OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new OrderHistoryFragment.OrderHistoryViewHolder(li, parent);
        }
    }

    private class OrderHistoryViewHolder extends RecyclerView.ViewHolder
    {
        private TextView rName;
        private TextView time;
        private TextView cost;
        private ImageView rImage;
        private Order order;
        private Restaurant restaurant;

        public OrderHistoryViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.history_list_selection, parent, false));

            // get UI element references
            rName = (TextView) itemView.findViewById(R.id.rName);
            cost = (TextView) itemView.findViewById(R.id.cost);
            time = (TextView) itemView.findViewById(R.id.time);
            rImage = (ImageView) itemView.findViewById(R.id.rImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ((AccountActivity) getActivity()).switchToRestaurantOrderFragment(order, restaurant);
                }
            });
        }

        public void bind(Order order)
        {
            this.order = order;
            this.restaurant = getRestaurant(order.getRestId());

            rName.setText(String.format("%s - %s", restaurant.getName(), restaurant.getLocation()));
            time.setText(String.format("%s at %s", order.getDate(), order.getTime()));
            cost.setText(String.format("RM %.2f", order.getTotal()));
            rImage.setImageResource(getResources().getIdentifier(removeExtension(restaurant.getImage()),
                    "drawable", getActivity().getPackageName()));
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ORDERLIST = "orderList";
    private static final String RESTLIST = "restList";

    // TODO: Rename and change types of parameters
    private ArrayList<Order> orderList;
    private ArrayList<Restaurant> restList;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OrderHistoryFragment newInstance(ArrayList<Order> orderList, ArrayList<Restaurant> restList) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ORDERLIST, orderList);
        args.putSerializable(RESTLIST, restList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderList = (ArrayList<Order>) getArguments().getSerializable(ORDERLIST);
            restList = (ArrayList<Restaurant>) getArguments().getSerializable(RESTLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        TextView title = view.findViewById(R.id.pageTitle);
        TextView emptyPrompt = view.findViewById(R.id.emptyPrompt);

        if(orderList.isEmpty())
        {
            emptyPrompt.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyPrompt.setVisibility(View.INVISIBLE);
        }

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewOrderHistory);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        OrderHistoryFragment.OrderHistoryAdapter adapter = new OrderHistoryFragment.OrderHistoryAdapter(orderList);
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

    private Restaurant getRestaurant(int restID)
    {
        // get restaurant name for food item
        for(Restaurant res: restList)
        {
            if(res.getId() == restID)
            {
                return res;
            }
        }
        return null;
    }
}