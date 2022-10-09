package curtin.edu.my.quickbites;

public class CartSchema {
    public static class CartTable
    {
        public static final String NAME = "cart";
        public static class Cols
        {
            public static final String CARTID = "cartId";
            public static final String CARTCONTENT = "cartContent";
        }
    }
}
