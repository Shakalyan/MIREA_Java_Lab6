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


    public static ArrayList<Mystery> getMysteries(int levelsCount, int complexitiesCount)
    {
        ArrayList<Mystery> allMysteries = new ArrayList<>();

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

                allMysteries.add(new Mystery(complexity, text, variants, correctVariantNumber));

                bufferedReader.readLine();
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            allMysteries = null;
        }

        return distributeMysteries(allMysteries, levelsCount, complexitiesCount);
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

    private static ArrayList<Mystery> distributeMysteries(ArrayList<Mystery> allMysteries, int levelsCount, int complexitiesCount)
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
