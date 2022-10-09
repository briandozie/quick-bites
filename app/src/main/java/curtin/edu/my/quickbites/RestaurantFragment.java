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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {

    private class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder>
    {
        private List<Restaurant> data;

        public RestaurantAdapter(List<Restaurant> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(RestaurantViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new RestaurantViewHolder(li, parent);
        }
    }

    private class RestaurantViewHolder extends RecyclerView.ViewHolder
    {
        private TextView rName;
        private TextView rLocation;
        private ImageView rImage;
        private Restaurant restaurant;

        public RestaurantViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.res_list_selection, parent, false));

            // get UI element references
            rName = (TextView) itemView.findViewById(R.id.fName);
            rLocation = (TextView) itemView.findViewById(R.id.serving);
            rImage = (ImageView) itemView.findViewById(R.id.fImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).switchToRestaurantMenuFragment(restaurant);
                }
            });
        }

        public void bind(Restaurant restaurant)
        {
            rName.setText(restaurant.getName());
            rLocation.setText(restaurant.getLocation());
            rImage.setImageResource(getResources().getIdentifier(removeExtension(restaurant.getImage()),
                    "drawable", getActivity().getPackageName()));

            this.restaurant = restaurant;
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESTAURANTLIST = "restaurantList";
    private static final String FOODLIST = "foodList";

    // TODO: Rename and change types of parameters
    private ArrayList<Restaurant> restaurantList;
    private ArrayList<Food> foodList;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    public static RestaurantFragment newInstance(ArrayList<Restaurant> rList, ArrayList<Food> foodList) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESTAURANTLIST, rList);
        args.putSerializable(FOODLIST, foodList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.restaurantList = (ArrayList<Restaurant>) getArguments().getSerializable(RESTAURANTLIST);
            this.foodList = (ArrayList<Food>) getArguments().getSerializable(FOODLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        TextView title = getActivity().findViewById(R.id.pageTitle);
        title.setText("Restaurants");

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewRestaurant);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RestaurantAdapter adapter = new RestaurantAdapter(restaurantList);
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
}