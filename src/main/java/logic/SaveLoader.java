package logic;

import objects.Location;
import objects.Mystery;
import objects.NPC;
import objects.Player;

import java.io.*;
import java.util.ArrayList;

public class SaveLoader implements Serializable
{
    private Player player;
    private ArrayList<Location> locations;
    private ArrayList<NPC> NPCs;
    private int turn;

    public SaveLoader(Player player, ArrayList<Location> locations, ArrayList<NPC> NPCs, int turn)
    {
        this.player = player;
        this.locations = locations;
        this.NPCs = NPCs;
        this.turn = turn;
    }

    public void save(String filePath) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public static SaveLoader load(String filePath) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SaveLoader saveLoader = (SaveLoader)ois.readObject();
        ois.close();
        return saveLoader;
    }

    public Player getPlayer()
    {
        return player;
    }

    public ArrayList<Location> getLocations()
    {
        return locations;
    }

    public ArrayList<NPC> getNPCs()
    {
        return NPCs;
    }

    public int getTurn()
    {
        return turn;
    }

}
