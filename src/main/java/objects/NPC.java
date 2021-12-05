package objects;

public class NPC
{

    private String name;
    private Mystery mystery;

    public NPC(String name, Mystery mystery)
    {
        this.name = name;
        this.mystery = mystery;
    }

    public String getName()
    {
        return name;
    }

}
