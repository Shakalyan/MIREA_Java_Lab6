package logic;

import interaction.Input;
import interaction.View;
import interaction.Phrases;
import objects.Location;
import objects.Mystery;
import objects.NPC;
import objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameLoop
{

    private static Player player;
    private static ArrayList<Location> locations;
    private static ArrayList<Mystery> mysteries;
    private static ArrayList<NPC> NPCs;

    private static final int levelsCount = 10;
    private static final int complexitiesCount = 5;


    public static void main(String[] args)
    {
        locations = LocationsReader.getLocations();
        ArrayList<Mystery> allMysteries = MysteriesReader.getMysteries();
        mysteries = getMysteriesSet(allMysteries);

        View.printlnPhrase(Phrases.getWelcomePhrase());

        View.printPhrase(Phrases.getPutNamePhrase());
        player = new Player(Input.getInput(), 100, 0);

        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));


        for(var l : mysteries)
            System.out.println(l);

    }


    public static Player getPlayer()
    {
        return player;
    }

    private static ArrayList<Mystery> getMysteriesSet(ArrayList<Mystery> allMysteries)
    {
        allMysteries.sort((m1, m2) -> m1.getComplexity() - m2.getComplexity());
        int allMysteriesCount = allMysteries.size();
        if(allMysteriesCount <= levelsCount)
            return allMysteries;

        int[] bounds = new int[complexitiesCount];
        int currentComplexity = 1;
        for(int i = 0; i < allMysteriesCount-1; ++i)
        {
            if(currentComplexity != allMysteries.get(i).getComplexity())
            {
                bounds[currentComplexity - 1] = i;
                ++currentComplexity;
            }
        }
        bounds[currentComplexity - 1] = allMysteriesCount;


        int averageMysteriesCount = levelsCount / complexitiesCount;
        Random random = new Random();
        ArrayList<Mystery> mysteriesSet = new ArrayList<>();
        for(int i = complexitiesCount - 1; i >= 0; --i)
        {
            int lowerBound = (i - 1 < 0)? 0 : bounds[i - 1];
            for(int j = 0; j < averageMysteriesCount; ++j)
            {
                if(bounds[i] - lowerBound <= 0)
                    break;
                int index = random.nextInt(lowerBound, bounds[i]);
                --bounds[i];
                mysteriesSet.add(allMysteries.get(index));
                allMysteries.set(index, null);
                Collections.swap(allMysteries, index, bounds[i]);
            }
        }

        if(mysteriesSet.size() != levelsCount)
        {
            int bound = levelsCount - mysteriesSet.size();
            for(int i = 0; i < bound; ++i)
            {
                if(allMysteries.get(i) != null)
                    mysteriesSet.add(allMysteries.get(i));
            }
        }

        mysteriesSet.sort((m1, m2) -> m1.getComplexity() - m2.getComplexity());
        return mysteriesSet;
    }

}
