package curtin.edu.my.quickbites;

public class OrderSchema
{
    public static class OrderTable
    {
        public static final String NAME = "customer_order";
        public static class Cols
        {
            public static final String ORDERID = "orderId";
            public static final String RESTID = "restId";
            public static final String ORDERTIME = "time";
            public static final String ORDERDATE = "date";
            public static final String TOTAL = "total";
            public static final String CONTENT = "content";
            public static final String USER = "user";
        }
    }
}
