package logic;

import objects.Location;
import objects.Mystery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LocationsReader
{

    private static File locationsFile = new File("Locations.txt");


    public static ArrayList<Location> getLocations(int levelsCount)
    {
        ArrayList<Location> locations = new ArrayList<>();

        try(FileReader reader = new FileReader(locationsFile); BufferedReader bufferedReader = new BufferedReader(reader))
        {
            String s = bufferedReader.readLine();
            while((s != null) && (!s.isBlank()))
            {
                String name = s;
                String description = "";
                s = bufferedReader.readLine();
                while((s != null) && (!s.isBlank()))
                {
                    description += s + "\n";
                    s = bufferedReader.readLine();
                }
                locations.add(new Location(name, description));
                s = bufferedReader.readLine();
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            locations = null;
        }

        return distributeLocations(locations, levelsCount);
    }

    public static ArrayList<Location> distributeLocations(ArrayList<Location> allLocations, int levelsCount)
    {
        ArrayList<Location> distributedLocations = new ArrayList<>();
        int bound = allLocations.size();
        Random random = new Random();
        for(int i = 0; i < levelsCount; ++i)
        {
            int index = random.nextInt(bound);
            --bound;
            distributedLocations.add(allLocations.get(index));
            Collections.swap(allLocations, index, bound);
            if(bound < 1)
                break;
        }

        if(distributedLocations.size() == levelsCount)
            return distributedLocations;

        int iterations = levelsCount - distributedLocations.size();
        for(int i = 0; i < iterations; ++i)
        {
            distributedLocations.add(allLocations.get(random.nextInt(allLocations.size())));
        }
        return distributedLocations;
    }

}
