import javax.swing.*;
import java.awt.*;

public class CatCoordinates
{

    public int X;
    public int Y;
    private int catNumber;
    private int levelSize;

    public CatCoordinates(int catNumber, int levelSize)
    {

        this.catNumber = catNumber;
        this.levelSize = levelSize;
        // atribui coordonatele corespunzatoare in functie de pozitia din vector
        if((catNumber % levelSize) == 0) // inseamna ca este elementul de pe ultima pozitie
        {
            Y = levelSize;
            X = catNumber / levelSize;
        }
        else
        {
            Y = catNumber % levelSize;
            X = catNumber / levelSize + 1;
        }
    }

    public void printPosition()
    {
        System.out.println("X: " + X + "\t" + "Y: " + Y);
    }

    public static int getButtonIndex(int x, int y, int levelSize)
    {
        return (x-1) * levelSize + y;
    }
}
