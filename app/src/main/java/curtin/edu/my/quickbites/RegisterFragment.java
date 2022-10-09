package curtin.edu.my.quickbites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACCOUNTLIST = "accountList";

    // TODO: Rename and change types of parameters
    private ArrayList<Account> accountList;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(ArrayList<Account> accountList) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNTLIST, accountList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountList = (ArrayList<Account>) getArguments().getSerializable(ACCOUNTLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        EditText username = (EditText) view.findViewById(R.id.usernameBox);
        EditText password = (EditText) view.findViewById(R.id.passwordBox);
        EditText cpassword = (EditText) view.findViewById(R.id.passwordBox2);
        Button registerBtn = (Button) view.findViewById(R.id.registerFragBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String cpass = cpassword.getText().toString();

                if(validateUsername(user, view)
                        && validatePassword(pass, view)
                        && validateCPassword(pass, cpass, view))
                {
                    // generate unique 6 digit cartId
                    Random rnd = new Random();
                    int cartId = 100000 + rnd.nextInt(900000);
                    ((AccountActivity) getActivity()).addAccount(new Account(user, pass, cartId, Account.FALSE));
                }
            }
        });

        return view;
    }

    private boolean validateCPassword(String password, String cpassword, View view)
    {
        boolean valid = false;
        if(password.equals(cpassword))
        {
            valid = true;
        }
        else
        {
            Snackbar.make(view, "Passwords do not match!", Snackbar.LENGTH_SHORT).show();
        }
        return valid;
    }


    private boolean validatePassword(String password, View view)
    {
        boolean valid = false;
        if(password.length() >= 4)
        {
            if(!password.contains(" "))
            {
                valid = true;
            }
            else
            {
                Snackbar.make(view, "Password cannot contain whitespaces!", Snackbar.LENGTH_SHORT).show();
            }
        }
        else
        {
            Snackbar.make(view, "Password must be at least 4 characters long!", Snackbar.LENGTH_SHORT).show();
        }
        return valid;
    }

    private boolean validateUsername(String username, View view)
    {
        boolean valid = false;
        if(username.length() > 0
                && !username.contains(" ")
                && username.contains("@")
                && username.contains(".com"))
        {
            if(!accountListContains(username))
            {
                valid = true;
            }
            else
            {
                Snackbar.make(view, "Email already exists!", Snackbar.LENGTH_SHORT).show();
            }
        }
        else
        {
            Snackbar.make(view, "Invalid email!", Snackbar.LENGTH_SHORT).show();
        }
        return valid;
    }

    private boolean accountListContains(String username)
    {
        for(Account acc: accountList)
        {
            if(acc.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }
}