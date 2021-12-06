package logic;

import interaction.Input;
import interaction.View;
import interaction.Phrases;
import objects.Location;
import objects.Mystery;
import objects.NPC;
import objects.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameLoop
{

    private static Player player;
    private static ArrayList<Location> locations;
    private static ArrayList<NPC> NPCs;
    private static int currentTurn = 0;

    private static final int levelsCount = 10;
    private static final int complexitiesCount = 5;

    private static final int baseAddedScore = 10;
    private static final int baseRemovedHP = 10;

    private static final String savePath = "Save.ser";


    public static void main(String[] args)
    {
        Log.startAutoLogger();

        View.printlnPhrase(Phrases.getWelcomePhrase());

        do
        {
            View.printlnPhrase(Phrases.getEmptyPhrase());
            View.printlnPhrase(Phrases.getTellingFacePhrase("System"));

            if(getUserYNAnswer(Phrases.getLoadSuggestPhrase()))
                loadGame();
            else
                loadNewGame();

            gameCycle();
        }
        while(getUserYNAnswer(Phrases.getContinueGamePhrase()));

        try
        {
            Log.stopAutoLogger();
        }
        catch(InterruptedException e)
        {
            Log.writeInfo("[GameLoop][getUserYNAnswer]: exception: " + e.getMessage());
        }

        View.printlnPhrase(Phrases.getEmptyPhrase());
        View.printlnPhrase(Phrases.getTellingFacePhrase("Logger"));
        Log.printHistory();
    }

    private static boolean getUserYNAnswer(String phrase)
    {
        String input = "";
        while((!input.equals("Y")) && (!input.equals("N")))
        {
            View.printPhrase(phrase);
            input = Input.getInput();
            Log.writeInfo("[GameLoop][getUserYNAnswer]: user has put " + input);
        }

        return input.equals("Y");
    }

    private static int getUserVariant(String phrase)
    {
        String input = "";
        while(true)
        {
            View.printPhrase(phrase);
            input = Input.getInput();
            Log.writeInfo("[GameLoop][getUserVariant]: user has put " + input);

            if(input.equals("exit"))
                return -1;
            if(input.matches("[0-9]+") && input.length() <= ((Mystery.getVariantsCount() - 1) / 10 + 1))
            {
                int v = Integer.parseInt(input);
                if((v >= 0) && (v < Mystery.getVariantsCount()))
                    return v;
            }
        }
    }

    private static void loadNewGame()
    {
        View.printPhrase(Phrases.getPutNamePhrase());

        player = new Player(Input.getInput(), 100, 0);
        View.printlnPhrase(Phrases.getHelloPhrase(player.getName()));
        locations = LocationsReader.getLocations(levelsCount);
        ArrayList<Mystery> mysteries = MysteriesReader.getMysteries(levelsCount, complexitiesCount);
        NPCs = generateNPCs(mysteries);
        currentTurn = 0;

        Log.writeInfo("[GameLoop][loadNewGame]: new game has started");
    }

    private static void loadGame()
    {
        SaveLoader loadedSL;
        try
        {
            loadedSL = SaveLoader.load(savePath);
            View.printlnPhrase(Phrases.getLoadSuccessfulPhrase());
            Log.writeInfo("[GameLoop][loadGame]: the game has been loaded ");

            player = loadedSL.getPlayer();
            locations = loadedSL.getLocations();
            NPCs = loadedSL.getNPCs();
            currentTurn = loadedSL.getTurn();
        }
        catch(IOException | ClassNotFoundException e)
        {
            View.printlnPhrase(Phrases.getLoadFaultPhrase());
            loadedSL = null;
            Log.writeInfo("[GameLoop][loadGame]: exception: " + e.getMessage());
        }
        View.printlnPhrase(Phrases.getEmptyPhrase());
    }

    private static void saveGame()
    {
        SaveLoader sl = new SaveLoader(player, locations, NPCs, currentTurn);
        try
        {
            sl.save(savePath);
            View.printlnPhrase(Phrases.getSaveSuccessfulPhrase());
            Log.writeInfo("[GameLoop][loadGame]: the game has been saved ");
        }
        catch(IOException e)
        {
            View.printlnPhrase(Phrases.getSaveFaultPhrase());
            Log.writeInfo("[GameLoop][loadGame]: exception: " + e.getMessage());
        }
    }


    private static void gameCycle()
    {
        Random random = new Random();
        for(int i = currentTurn; i < levelsCount; ++i)
        {
            currentTurn = i;
            Location currentLocation = locations.get(i);
            NPC currentNPC = NPCs.get(i);
            Mystery currentMystery = currentNPC.getMystery();

            View.printlnPhrase(Phrases.getTellingFacePhrase("Storyteller"));
            View.printlnPhrase(Phrases.getLocationChangingPhrase(currentLocation));
            View.printlnPhrase(Phrases.getMeetNPCPhrase());

            View.printlnPhrase(Phrases.getTellingFacePhrase(currentNPC.getName()));
            View.printlnPhrase(Phrases.getNPCStartPhrase(player.getName(), currentNPC));

            View.printlnPhrase(Phrases.getTellingFacePhrase("Storyteller"));
            View.printlnPhrase(Phrases.getAnswerPhrase());

            View.printlnPhrase(Phrases.getEmptyPhrase());
            View.printlnPhrase(Phrases.getTellingFacePhrase(player.getName()));
            int answer = getUserVariant(Phrases.getAskVariantPhrase());
            if(answer == -1)
            {
                View.printlnPhrase(Phrases.getEmptyPhrase());
                View.printlnPhrase(Phrases.getTellingFacePhrase("System"));
                if(getUserYNAnswer(Phrases.getSaveSuggestPhrase()))
                {
                    saveGame();
                }
                return;
            }
            View.printlnPhrase(Phrases.getEmptyPhrase());

            if(currentMystery.answerIsCorrect(answer))
            {
                View.printlnPhrase(Phrases.getTellingFacePhrase(currentNPC.getName()));
                View.printlnPhrase(Phrases.getNPCCorrectAnswerPhrase());
                View.printlnPhrase(Phrases.getTellingFacePhrase("Storyteller"));

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
                View.printlnPhrase(Phrases.getTellingFacePhrase("Storyteller"));

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
            Log.writeInfo("[GameLoop][gameCycle]: " + i + " iteration has ended");
        }
        View.printlnPhrase(Phrases.getEmptyPhrase());
        View.printlnPhrase(Phrases.getTellingFacePhrase("System"));
        View.printlnPhrase(Phrases.getEndGameStatsPhrase(player.getScore()));
        Log.writeInfo("[GameLoop][gameCycle]: gameCycle has ended");
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
