package curtin.edu.my.quickbites;

import java.io.Serializable;

public class Account implements Serializable
{
    public static final int TRUE = 0;
    public static final int FALSE = 1;

    private String username;
    private String password;
    private int cartId;
    private int login;

    public Account(String username, String password, int cartId, int login)
    {
        this.username = username;
        this.password = password;
        this.cartId = cartId;
        this.login = login;
    }

    //accessors
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCartId() {
        return cartId;
    }

    public int getLogin() {
        return login;
    }

    // mutator
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setLogin(int login) {
        this.login = login;
    }
}
