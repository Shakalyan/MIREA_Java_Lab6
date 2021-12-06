package objects;

import java.io.Serializable;

public class Location implements Serializable
{
    private String name;
    private String description;

    public Location(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

}
