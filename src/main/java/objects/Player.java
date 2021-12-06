package objects;

import java.io.Serializable;

public class Player implements Serializable
{
    private String name;
    private int hitPoints;
    private int score;

    public Player(String name, int hitPoints, int score)
    {
        this.name = name;
        this.hitPoints = hitPoints;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public void decreaseHitPoints(int points)
    {
        hitPoints = (hitPoints - points < 0) ? 0 : hitPoints - points;
    }

    public int getScore()
    {
        return score;
    }

    public void increaseScore(int points)
    {
        score += points;
    }

}
