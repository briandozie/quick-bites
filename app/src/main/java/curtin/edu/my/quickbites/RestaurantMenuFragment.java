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
import java.util.List;

public class RestaurantMenuFragment extends Fragment {

    private class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuViewHolder>
    {
        private List<Food> data;

        public RestaurantMenuAdapter(List<Food> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(RestaurantMenuViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public RestaurantMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new RestaurantMenuViewHolder(li, parent);
        }
    }

    private class RestaurantMenuViewHolder extends RecyclerView.ViewHolder
    {
        private TextView fName;
        private TextView fDesc;
        private TextView fPrice;
        private ImageView fImage;
        private Food food;

        public RestaurantMenuViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.food_list_selection, parent, false));

            // get UI element references
            fName = (TextView) itemView.findViewById(R.id.foodName);
            fDesc = (TextView) itemView.findViewById(R.id.foodDesc);
            fPrice = (TextView) itemView.findViewById(R.id.fPrice);
            fImage = (ImageView) itemView.findViewById(R.id.foodImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ((MainActivity) getActivity()).showDialog(restaurant, food);
                }
            });
        }

        public void bind(Food food)
        {
            fName.setText(food.getName());
            fDesc.setText(food.getDesc());
            fPrice.setText(String.format("RM %.2f", food.getCost()));
            fImage.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                    "drawable", getActivity().getPackageName()));

            this.food = food;
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FOODLIST = "foodList";
    private static final String RESTAURANT = "restaurant";

    // TODO: Rename and change types of parameters
    private ArrayList<Food> foodList;
    private Restaurant restaurant;

    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantMenuFragment newInstance(ArrayList<Food> fList, Restaurant restaurant)
    {
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
        Bundle args = new Bundle();
        args.putSerializable(FOODLIST, fList);
        args.putSerializable(RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.foodList = (ArrayList<Food>) getArguments().getSerializable(FOODLIST);
            this.restaurant = (Restaurant) getArguments().getSerializable(RESTAURANT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        TextView title = getActivity().findViewById(R.id.pageTitle);
        title.setText(String.format("%s - %s", restaurant.getName(), restaurant.getLocation()));

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewRestaurantMenu);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Food> restaurantMenu = getFromFromRestaurant(restaurant);
        RestaurantMenuAdapter adapter = new RestaurantMenuAdapter(restaurantMenu);
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

    private ArrayList<Food> getFromFromRestaurant(Restaurant res)
    {
        ArrayList<Food> restaurantMenu = new ArrayList<>();
        for(Food food: foodList)
        {
            if(food.getRestId() == res.getId())
            {
                restaurantMenu.add(food);
            }
        }
        return restaurantMenu;
    }
}