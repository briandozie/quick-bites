package curtin.edu.my.quickbites;

import java.io.Serializable;

public class RestaurantSchema implements Serializable
{
    public static class RestaurantTable implements Serializable
    {
        public static final String NAME = "restaurants";
        public static class Cols
        {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String IMAGE = "image";
            public static final String LOCATION = "location";
        }
    }
}
