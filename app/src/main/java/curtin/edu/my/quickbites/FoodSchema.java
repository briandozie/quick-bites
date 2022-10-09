package curtin.edu.my.quickbites;

import java.io.Serializable;

public class FoodSchema implements Serializable
{
    public static class FoodTable implements Serializable
    {
        public static final String NAME = "food";
        public static class Cols
        {
            public static final String FOODID = "foodId";
            public static final String RESTID = "restId";
            public static final String NAME = "name";
            public static final String COST = "cost";
            public static final String DESC = "desc";
            public static final String IMAGE = "image";
        }
    }
}
