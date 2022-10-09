package curtin.edu.my.quickbites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class CartDialogFragment extends DialogFragment
{
    private static Food food;
    private static Restaurant res;
    private static Cart cart;
    private int quantity = 0;
    private AlertDialog dialog;

    public static CartDialogFragment newInstance(Restaurant inRes, Food inFood, Cart inCart)
    {
        CartDialogFragment dialogFragment = new CartDialogFragment();
        res = inRes;
        food = inFood;
        cart = inCart;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cart_dialog, null);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView quantityBox = (TextView) view.findViewById(R.id.quantity);
        Button add = (Button) view.findViewById(R.id.addBut);
        Button minus = (Button) view.findViewById(R.id.minusBut);

        image.setImageResource(getResources().getIdentifier(removeExtension(food.getImage()),
                "drawable", getActivity().getPackageName()));
        name.setText(food.getName());
        quantityBox.setText("0");
        builder.setView(view)
                .setPositiveButton(R.string.add_to_cart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cart.addFood(res, food, quantity);
                        ((MainActivity) getActivity()).addToCart(res, food, quantity);
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
                dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 0) {
                    quantity--;
                    quantityBox.setText(String.valueOf(quantity));
                }
                if(quantity == 0)
                {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
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