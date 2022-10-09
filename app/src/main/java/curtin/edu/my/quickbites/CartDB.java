package curtin.edu.my.quickbites;

public class CartDB {
    private int cartId;
    private Cart cart;

    public CartDB(int cartId, Cart cart)
    {
        this.cartId = cartId;
        this.cart = cart;
    }

    public int getCartId() {
        return cartId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
