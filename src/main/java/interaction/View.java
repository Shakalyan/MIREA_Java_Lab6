package interaction;

public class View
{

    public static void printlnPhrase(String phrase)
    {
        println(phrase);
    }

    public static void printPhrase(String phrase)
    {
        print(phrase);
    }

    private static void println(String s)
    {
        System.out.println(s);
    }

    private static void print(String s)
    {
        System.out.print(s);
    }

}
