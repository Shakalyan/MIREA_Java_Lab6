package logic;

import objects.Location;
import objects.Mystery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LocationsReader
{

    private static File locationsFile = new File("Locations.txt");


    public static ArrayList<Location> getLocations()
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

        return locations;
    }

}
