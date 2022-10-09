package curtin.edu.my.quickbites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.HashMap;

public class CartEditDialogFragment extends DialogFragment {

    public interface CartEditDialogListener
    {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    CartEditDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            listener = (CartEditDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException("CartEditDialogListener not implemented");
        }
    }

    private static Food food;
    private static Restaurant res;
    private static Cart cart;
    private static int quantity;
    private static int oriQuantity;
    private static HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> foodCartMap;
    private AlertDialog dialog;

    public static CartEditDialogFragment newInstance(Restaurant inRes, Food inFood, Cart inCart, int inQuantity,
                                                     HashMap<Integer, AbstractMap.SimpleEntry<Double, Integer>> inFoodCartMap)
    {
        CartEditDialogFragment dialogFragment = new CartEditDialogFragment();
        res = inRes;
        food = inFood;
        cart = inCart;
        quantity = inQuantity;
        oriQuantity = inQuantity;
        foodCartMap = inFoodCartMap;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cart_edit_dialog, null);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView quantityBox = (TextView) view.findViewById(R.id.quantity);
        Button add = (Button) view.findViewById(R.id.addBut);
        Button minus = (Button) view.findViewById(R.id.minusBut);

        image.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                "drawable", getActivity().getPackageName()));
        name.setText(food.getName());
        quantityBox.setText(String.valueOf(quantity));
        builder.setView(view)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(quantity < oriQuantity)
                        {
                            ((CartActivity) getActivity()).removeFromCart(res, food, oriQuantity - quantity);
                            foodCartMap.get(food.getFoodId()).setValue(quantity);
                        }
                        else
                        {
                            ((CartActivity) getActivity()).addToCart(res, food, quantity - oriQuantity);
                        }
                        foodCartMap.get(food.getFoodId()).setValue(quantity);
                        listener.onDialogPositiveClick(CartEditDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                quantityBox.setText(String.valueOf(quantity));

                if(quantity != oriQuantity)
                {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 0) {
                    quantity--;
                    quantityBox.setText(String.valueOf(quantity));
                }
                if(quantity != oriQuantity)
                {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
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