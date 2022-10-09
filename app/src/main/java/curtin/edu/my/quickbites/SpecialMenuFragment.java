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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SpecialMenuFragment extends Fragment {

    private class SpecialMenuAdapter extends RecyclerView.Adapter<SpecialMenuViewHolder>
    {
        private List<Food> data;

        public SpecialMenuAdapter(List<Food> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(SpecialMenuViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public SpecialMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new SpecialMenuViewHolder(li, parent);
        }
    }

    private class SpecialMenuViewHolder extends RecyclerView.ViewHolder
    {
        private TextView fName;
        private TextView fDesc;
        private TextView fPrice;
        private ImageView fImage;
        private Food food;
        private Restaurant res;

        public SpecialMenuViewHolder(LayoutInflater li, ViewGroup parent)
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
                    ((MainActivity) getActivity()).showDialog(res, food);
                }
            });
        }

        public void bind(Food food)
        {
            Restaurant res = getRestaurant(food.getRestId());
            fName.setText(String.format("%s - %s", food.getName(), res.getName()));
            fDesc.setText(food.getDesc());
            fPrice.setText(String.format("RM %.2f", food.getCost()));
            fImage.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                    "drawable", getActivity().getPackageName()));

            this.res = res;
            this.food = food;
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FOODLIST = "foodList";
    private static final String RESTAURANTLIST = "restaurantList";
    private static final String MAP = "map";

    // TODO: Rename and change types of parameters
    private ArrayList<Food> foodList;
    private ArrayList<Restaurant> restaurantList;
    private ArrayList<Food> specialMenu;

    public SpecialMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SpecialMenuFragment newInstance(ArrayList<Food> fList, ArrayList<Restaurant> rList)
    {
        Map<String, Integer> map = new HashMap<>();
        SpecialMenuFragment fragment = new SpecialMenuFragment();
        Bundle args = new Bundle();
        args.putSerializable(FOODLIST, fList);
        args.putSerializable(RESTAURANTLIST, rList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.foodList = (ArrayList<Food>) getArguments().getSerializable(FOODLIST);
            this.restaurantList = (ArrayList<Restaurant>) getArguments().getSerializable(RESTAURANTLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special_menu, container, false);

        TextView title = getActivity().findViewById(R.id.pageTitle);
        title.setText("Today's Special");

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewSpecial);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        specialMenu = createSpecialMenu();
        SpecialMenuAdapter adapter = new SpecialMenuAdapter(specialMenu);
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

    // get one random food from each restaurant
    private ArrayList<Food> createSpecialMenu()
    {
        ArrayList<Food> specialMenu = new ArrayList<>();
        Random rand = new Random();

        for(Restaurant res: restaurantList)
        {
            ArrayList<Food> restaurantMenu = new ArrayList<>();
            for(Food food: foodList)
            {
                if(food.getRestId() == res.getId())
                {
                    restaurantMenu.add(food);
                }
            }
            specialMenu.add(restaurantMenu.get(rand.nextInt(restaurantMenu.size())));
        }
        return specialMenu;
    }

    private Restaurant getRestaurant(int restID)
    {
        for(Restaurant res: restaurantList)
        {
            if(res.getId() == restID)
            {
                return res;
            }
        }
        return null;
    }
}