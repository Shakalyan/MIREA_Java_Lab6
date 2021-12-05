package logic;

import interaction.Input;
import interaction.View;
import interaction.Phrases;
import objects.Player;

public class GameLoop
{

    private static Player player;

    public static void main(String[] args)
    {

        View.printlnPhrase(Phrases.getWelcomePhrase());

        View.printPhrase(Phrases.getPutNamePhrase());
        player = new Player(Input.getInput(), 100, 0);

        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));

    }


    public Player getPlayer()
    {
        return player;
    }

}
