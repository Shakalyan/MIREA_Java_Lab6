package interaction;

import objects.Location;
import objects.NPC;
import objects.Player;

public class Phrases
{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static String getEmptyPhrase()
    {
        return "";
    }

    public static String getWelcomePhrase()
    {
        return ANSI_GREEN + "Welcome to the LAB-6!" + ANSI_RESET;
    }

    public static String getContinueGamePhrase()
    {
        return "Do you want to continue?[Y/N]: ";
    }

    public static String getTellingFacePhrase(String face)
    {
        return ANSI_YELLOW + "<" + face + ">" + ANSI_RESET;
    }

    public static String getLoadSuggestPhrase()
    {
        return "Do you want to load previous game?[Y/N]: ";
    }

    public static String getLoadSuccessfulPhrase()
    {
        return ANSI_GREEN + "Loaded successfully!" + ANSI_RESET;
    }

    public static String getLoadFaultPhrase()
    {
        return ANSI_RED + "Bad load" + ANSI_RESET;
    }

    public static String getSaveSuggestPhrase()
    {
        return "Do you want to save this game?[Y/N]: ";
    }

    public static String getSaveSuccessfulPhrase()
    {
        return ANSI_GREEN + "Saved successfully!" + ANSI_RESET;
    }

    public static String getSaveFaultPhrase()
    {
        return ANSI_RED + "Bad save" + ANSI_RESET;
    }

    public static String getAskVariantPhrase()
    {
        return "Print a number from 0 to 3 or 'exit' if you want to exit: ";
    }

    public static String getPutNamePhrase()
    {
        return "Put your name here: ";
    }

    public static String getHelloPhrase(String playerName)
    {
        return "Hello, " + playerName + "! Game's starting...\n";
    }

    public static String getLocationChangingPhrase(Location location)
    {
        return  "Morning has come, you are full of energy and ready to explore new places.\n" +
                "After a few hours of a tiring ride, you finally reach your goal - " + location.getName() + ".\n" +
                "Based on rumors about these places and stories of the inhabitants,\n" +
                "you can make the following description to it:\n" + location.getDescription();
    }

    public static String getMeetNPCPhrase()
    {
        return  "While walking and trying to find something interesting, you suddenly notice\n" +
                "something shiny in the bushes. Trying to reach the thing, you are suddenly\n" +
                "slapped on the shoulder. Turning around, you see an old man in front of you.\n" +
                "Without waiting for your reaction, the old man begins:\n";
    }

    public static String getNPCStartPhrase(String playerName, NPC npc)
    {
        return  "Good day, " + playerName + ". My name is " + npc.getName() + ".\n" +
                "I know you - you often came to me in dreams and answered my riddle...\n" +
                "So listen to the next one:\n" + npc.getMystery();
    }

    public static String getAnswerPhrase()
    {
        return "Without thinking twice, you give an answer...";
    }

    public static String getNPCCorrectAnswerPhrase()
    {
        return "Finally! Thank you, traveler. I will never disturb you again...";
    }

    public static String getSTCorrectAnswerPhrase()
    {
        return  "\nThe old man falls at your feet and seems completely lifeless.\n" +
                "You can't think of anything better than to take the contents of his pockets before anyone else.\n" +
                "Stunned by this event, you completely forget about the thing and go away...";
    }

    public static String getSTWrongAnswerPhrase()
    {
        return  "The old man looks at you for a long time. His eyes are filled with anger.\n" +
                "Suddenly your eyes darken sharply ... When you come to your senses, you find that neither\n" +
                "the bush nor the old man is there. Incomprehensible internal anxiety and headache prevents\n" +
                "you from thinking. In the hope that this will pass, you move on...";
    }

    public static String getWinPhrase()
    {
        return  "The old man falls at your feet and seems completely lifeless.\n" +
                "This time you decide to leave the body of the dead man alone,\n" +
                "and find out what is in the bushes. Having rummaged in them,\n" +
                "you will not find anything in them. With a shrug, you move on.\n" +
                "The headache gradually recedes...";
    }

    public static String getLosePhrase()
    {
        return  "The old man looks at you for a long time. His eyes are filled with despair.\n" +
                "Suddenly he turns abruptly and leaves. You look after him with tired eyes.\n" +
                "The headache becomes so unbearable that you lie down, unable to stand up on your own anymore...";
    }

    public static String getScoresIncreasePhrase(int addedScores, int currentScores)
    {
        return ANSI_GREEN + "\n<Your scores are increased by " + addedScores + ". Now it's " + currentScores + ">\n" + ANSI_RESET;
    }

    public static String getHPDecreasePhrase(int removedPoints, int currentPoints)
    {
        return ANSI_RED + "\n<Your hit points are decreased by " + removedPoints + ". Now it's " + currentPoints + ">\n" + ANSI_RESET;
    }

    public static String getEndGameStatsPhrase(int score)
    {
        return ANSI_GREEN + "Game's finished! Your score: " + score + "." + ANSI_RESET;
    }

}
