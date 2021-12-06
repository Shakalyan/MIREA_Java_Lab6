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

    private static final int levelsCount = 5;
    private static final int complexitiesCount = 5;

    private static final int baseAddedScore = 10;
    private static final int baseRemovedHP = 10;

    private static final int phrasesDelay = 1000;


    public static void main(String[] args) throws InterruptedException
    {
        locations = LocationsReader.getLocations();
        mysteries = MysteriesReader.getMysteries(levelsCount, complexitiesCount);
        NPCs = generateNPCs(mysteries);

        View.printlnPhrase(Phrases.getWelcomePhrase());
        View.printPhrase(Phrases.getPutNamePhrase());
        player = new Player(Input.getInput(), 100, 0);
        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));

        Random random = new Random();
        for(int i = 0; i < levelsCount; ++i)
        {
            Location currentLocation = locations.get(random.nextInt(locations.size()));
            NPC currentNPC = NPCs.get(i);
            Mystery currentMystery = currentNPC.getMystery();

            View.printlnPhrase(Phrases.getLocationChangingPhrase(currentLocation));
            View.printlnPhrase(Phrases.getMeetNPCPhrase());
            View.printlnPhrase(Phrases.getNPCStartPhrase(player.getName(), currentNPC));
            View.printlnPhrase(Phrases.getAnswerPhrase());

            int answer = Input.getIntInput();
            if(currentMystery.answerIsCorrect(answer))
            {
                View.printlnPhrase(Phrases.getNPCCorrectAnswerPhrase());
                int addedScores = baseAddedScore * currentMystery.getComplexity();
                player.increaseScore(addedScores);
                if(i + 1 != levelsCount)
                {
                    View.printlnPhrase(Phrases.getSTCorrectAnswerPhrase());
                    View.printlnPhrase(Phrases.getScoresIncreasePhrase(addedScores, player.getScore()));
                }
                else
                {
                    View.printlnPhrase(Phrases.getWinPhrase());
                }
            }
            else
            {
                int removedHP = baseRemovedHP * currentMystery.getComplexity();
                player.decreaseHitPoints(removedHP);
                if(player.getHitPoints() <= 0)
                {
                    View.printlnPhrase(Phrases.getLosePhrase());
                    break;
                }
                else
                {
                    View.printlnPhrase(Phrases.getSTWrongAnswerPhrase());
                    View.printlnPhrase(Phrases.getHPDecreasePhrase(removedHP, player.getHitPoints()));
                }
            }
        }

        View.printlnPhrase(Phrases.getEndGameStatsPhrase(player.getScore()));

    }


    public static Player getPlayer()
    {
        return player;
    }

    private static ArrayList<NPC> generateNPCs(ArrayList<Mystery> mysteries)
    {
        ArrayList<NPC> npcs = new ArrayList<>();
        for(int i = 0; i < mysteries.size(); ++i)
        {
            npcs.add(new NPC(mysteries.get(i)));
        }
        return npcs;
    }

}
