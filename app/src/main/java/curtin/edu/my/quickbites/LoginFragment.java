package curtin.edu.my.quickbites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACCOUNTLIST = "accountList";

    // TODO: Rename and change types of parameters
    private ArrayList<Account> accountList;

    public LoginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(ArrayList<Account> accountList) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText username = (EditText) view.findViewById(R.id.usernameBox);
        EditText password = (EditText) view.findViewById(R.id.passwordBox);
        TextView registerBtn = (TextView) view.findViewById(R.id.registerBtn);
        Button loginBtn = (Button) view.findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                password.setText("");
                ((AccountActivity) getActivity()).switchToRegisterFragment();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean exist = false;
                String user = username.getText().toString();
                String pass = password.getText().toString();

                for(Account acc: accountList)
                {
                    if (acc.getUsername().equals(user))
                    {
                        exist = true;
                        if(acc.getPassword().equals(pass))
                        {
                            ((AccountActivity) getActivity()).loginUser(acc);
                            break;
                        }
                        else
                        {
                            Snackbar.make(view, "Incorrect password! Please try again", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
                // no account found
                if(!exist)
                {
                    Snackbar.make(view, "Account does not exist!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
        return view;
    }
}