package curtin.edu.my.quickbites;

import java.io.Serializable;

public class Restaurant implements Serializable
{
    private int id;
    private String name;
    private String image;
    private String location;

    public Restaurant(int id, String name, String image, String location)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.location = location;
    }

    // accessors
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    // mutators

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
