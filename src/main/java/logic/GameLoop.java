package logic;

import interaction.Input;
import interaction.View;
import interaction.Phrases;
import objects.Location;
import objects.Mystery;
import objects.NPC;
import objects.Player;

import java.util.ArrayList;

public class GameLoop
{

    private static Player player;
    private static ArrayList<Location> locations;
    private static ArrayList<Mystery> mysteries;
    private static ArrayList<NPC> NPCs;


    public static void main(String[] args)
    {
        locations = LocationsReader.getLocations();
        mysteries = MysteriesReader.getMysteries();


        View.printlnPhrase(Phrases.getWelcomePhrase());

        View.printPhrase(Phrases.getPutNamePhrase());
        player = new Player(Input.getInput(), 100, 0);

        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));





        for(var l : mysteries)
            System.out.println(l);

    }


    public Player getPlayer()
    {
        return player;
    }

}
