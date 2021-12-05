package logic;

import objects.Mystery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MysteriesReader
{
    private static File mysteriesFile = new File("Mysteries.txt");


    public static ArrayList<Mystery> getMysteries()
    {
        ArrayList<Mystery> mysteries = new ArrayList<>();

        try(FileReader reader = new FileReader(mysteriesFile); BufferedReader bufferedReader = new BufferedReader(reader))
        {
            String s;
            while((s = bufferedReader.readLine()) != null)
            {
                String text = s;
                ArrayList<String> variants = new ArrayList<String>();
                for(int i = 0; i < Mystery.getVariantsCount(); ++i)
                {
                    s = bufferedReader.readLine();
                    variants.add(s);
                }
                String correctVariant = variants.get(0);
                variants = getRandomizedVariants(variants);
                int correctVariantNumber = getCorrectVariantNumber(variants, correctVariant);

                int complexity = Integer.parseInt(bufferedReader.readLine());

                mysteries.add(new Mystery(complexity, text, variants, correctVariantNumber));

                bufferedReader.readLine();
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            mysteries = null;
        }

        return mysteries;
    }

    private static ArrayList<String> getRandomizedVariants(ArrayList<String> variants)
    {
        ArrayList<String> randomizedVariants = new ArrayList<String>();
        Random random = new Random();
        int bound = Mystery.getVariantsCount();
        for(int i = 0; i < Mystery.getVariantsCount()-1; ++i)
        {
            int index = random.nextInt(0, bound);
            randomizedVariants.add(variants.get(index));
            Collections.swap(variants, index, bound-1);
            --bound;
        }
        randomizedVariants.add(variants.get(0));
        return randomizedVariants;
    }

    private static int getCorrectVariantNumber(ArrayList<String> variants, String correctVariant)
    {
        for(int i = 0; i < Mystery.getVariantsCount(); ++i)
            if(variants.get(i).equals(correctVariant))
                return i;
        return 0;
    }

}
