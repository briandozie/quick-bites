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

import com.google.android.material.snackbar.Snackbar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantCartFragment extends Fragment {

    private class RestaurantCartAdapter extends RecyclerView.Adapter<RestaurantCartFragment.RestaurantCartViewHolder>
    {
        private List<Restaurant> data;

        public RestaurantCartAdapter(List<Restaurant> data) { this.data = data; }
        @Override public int getItemCount() { return data.size(); }
        @Override public void onBindViewHolder(RestaurantCartFragment.RestaurantCartViewHolder vh, int index) { vh.bind(data.get(index)); };

        @Override
        public RestaurantCartFragment.RestaurantCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType0)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new RestaurantCartFragment.RestaurantCartViewHolder(li, parent, this);
        }

        public void removeItem(int position)
        {
            itemRemoved = true;
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size());
        }
    }

    private class RestaurantCartViewHolder extends RecyclerView.ViewHolder
    {
        private TextView rName;
        private TextView items;
        private ImageView rImage;
        private Restaurant restaurant;
        private Button removeBtn;

        public RestaurantCartViewHolder(LayoutInflater li, ViewGroup parent, RestaurantCartAdapter adapter)
        {
            super(li.inflate(R.layout.cart_restaurant_selection, parent, false));
            removeBtnList.add(itemView.findViewById(R.id.removeBtn));

            // get UI element references
            rName = (TextView) itemView.findViewById(R.id.fName);
            items = (TextView) itemView.findViewById(R.id.serving);
            rImage = (ImageView) itemView.findViewById(R.id.fImage);
            removeBtn = itemView.findViewById(R.id.removeBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((CartActivity) getActivity()).switchToFoodCartFragment(restaurant);

                }
            });

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.removeItem(getBindingAdapterPosition());
                    cart = ((CartActivity) getActivity()).removeRestaurantFromCart(restaurant.getId());
                }
            });
        }

        public void bind(Restaurant restaurant)
        {
            rName.setText(String.format("%s - %s", restaurant.getName(), restaurant.getLocation()));
            items.setText(cart.getRestaurantItemQuantity(restaurant.getId()) + " items(s)");
            rImage.setImageResource(getResources().getIdentifier(removeExtension(restaurant.getImage()),
                    "drawable", getActivity().getPackageName()));

            this.restaurant = restaurant;
            if(itemRemoved)
            {
                showRemoveButton();
            }
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CART = "cart";

    // TODO: Rename and change types of parameters
    private Cart cart;
    private ArrayList<Button> removeBtnList;
    private boolean itemRemoved;
    private RestaurantCartFragment.RestaurantCartAdapter adapter;

    public RestaurantCartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RestaurantCartFragment newInstance(Cart cart) {
        RestaurantCartFragment fragment = new RestaurantCartFragment();
        Bundle args = new Bundle();
        args.putSerializable(CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.cart = (Cart) getArguments().getSerializable(CART);
            removeBtnList = new ArrayList<>();
            itemRemoved = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getSupportFragmentManager().beginTransaction().show(RestaurantCartFragment.this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_cart, container, false);

        TextView subTitle = getActivity().findViewById(R.id.subTitle);
        subTitle.setText("Restaurants");

        if(!cart.isEmpty())
        {
            ((CartActivity) getActivity()).findViewById(R.id.noItemBox).setVisibility(View.INVISIBLE);
            ((CartActivity) getActivity()).findViewById(R.id.cartPic).setVisibility(View.INVISIBLE);

            RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewRestaurantCart);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new RestaurantCartFragment.RestaurantCartAdapter(cart.getRestaurantList());
            rv.setAdapter(adapter);
        }
        else
        {
            ((CartActivity) getActivity()).findViewById(R.id.noItemBox).setVisibility(View.VISIBLE);
            ((CartActivity) getActivity()).findViewById(R.id.cartPic).setVisibility(View.VISIBLE);
        }


        return view;
    }

    public void showRemoveButton()
    {
        for(Button removeBtn: removeBtnList)
        {
            removeBtn.setVisibility(View.VISIBLE);
        }
    }

    public void hideRemoveButton()
    {
        for(Button removeBtn: removeBtnList)
        {
            removeBtn.setVisibility(View.INVISIBLE);
        }
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

    public void updateUI()
    {
        adapter.notifyDataSetChanged();
    }

}