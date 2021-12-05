package objects;

import java.util.ArrayList;

public class Mystery
{

    private String text;

    private int complexity;

    private ArrayList<String> variants;
    private int correctVariant;
    private static final int variantsCount = 4;

    public Mystery(int complexity, String text, ArrayList<String> variants, int correctVariant)
    {
        this.text = text;
        this.complexity = complexity;
        this.variants = variants;
        this.correctVariant = correctVariant;
    }

    public static int getVariantsCount()
    {
        return variantsCount;
    }

    public int getComplexity()
    {
        return complexity;
    }

    public boolean answerIsCorrect(int answer)
    {
        return (answer == correctVariant);
    }

    @Override
    public String toString()
    {
        String output = text + "\nVariants:\n";
        for(int i = 0; i < variantsCount; ++i)
            output += i + ". " + variants.get(i) + "\n";

        return output;

    }

}
