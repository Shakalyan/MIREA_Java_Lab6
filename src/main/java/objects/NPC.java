package objects;

import java.util.ArrayList;
import java.util.Random;

public class NPC
{

    private String name;
    private Mystery mystery;

    private static ArrayList<String> names;

    static
    {
        names = new ArrayList<>();
        names.add("William");
        names.add("Robert");
        names.add("Rohyr");
        names.add("Walter");
        names.add("Henry");
        names.add("Adam");
        names.add("Gilbert");
        names.add("Wigmar");
        names.add("Saiman");
        names.add("Godwin");
    }

    public NPC(Mystery mystery)
    {
        this.name = names.get(new Random().nextInt(names.size()));
        this.mystery = mystery;
    }

    public String getName()
    {
        return name;
    }

    public Mystery getMystery()
    {
        return mystery;
    }

}
