package logic;

import interaction.Input;
import interaction.View;
import interaction.Phrases;
import objects.Location;
import objects.Mystery;
import objects.Player;

import java.util.ArrayList;

public class GameLoop
{

    private static Player player;
    private static ArrayList<Location> locations;


    public static void main(String[] args)
    {

        View.printlnPhrase(Phrases.getWelcomePhrase());

        View.printPhrase(Phrases.getPutNamePhrase());
        player = new Player(Input.getInput(), 100, 0);

        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));

        ArrayList<Mystery> mysteries = MysteriesReader.getMysteries();

        locations = LocationsReader.getLocations();

        for(var l : locations)
            System.out.println(l);

    }


    public Player getPlayer()
    {
        return player;
    }

}
