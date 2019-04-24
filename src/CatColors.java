

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// clasa care adreseaza lucrul cu imaginile. Generez imaginile pentru nivelul dorit

public class CatColors {


    private String pathName;
    private LevelsAttributes levelsAttributes;

    CatColors() {
        buildCats();
    }

    public void buildCats() {

        levelsAttributes = new LevelsAttributes();


        pathName = "C:/Linux/proiecte_java/CatCorner/src/resources/";

    }

    public JButton generateButtonF(int cat, int levelSize)
    {
        JButton myButton = new JButton();
        String currentValue = pathName + (levelSize - 4) + "/";
        Image image;
        try
        {
            image = ImageIO.read(new File(currentValue + FootprintsColor.valueOf(cat) + ".png"));
            myButton.setIcon(new ImageIcon(image));
        }
        catch (IOException e)
        {
            System.out.println(currentValue + FootprintsColor.valueOf(cat) + ".png");
        }

        return myButton;
    }

    public JButton generateButton(int cat, int levelSize)
    {

        JButton myButton = new JButton();
        String currentValue = pathName + (levelSize - 4) + "/";
        Image image;
        try
        {
            image = ImageIO.read(new File(currentValue + CatColor.valueOf(cat) + ".png"));
            myButton.setIcon(new ImageIcon(image));
        }
        catch (IOException e)
        {
            System.out.println(currentValue + CatColor.valueOf(cat) + ".png");
        }

        return myButton;
    }

    public JButton generateButtonW(int cat, int levelSize, int wildCat)
    {

        JButton myButton = new JButton();
        String currentValue = pathName + (levelSize - 4) + "/";
        Image image;
        try
        {
            image = ImageIO.read(new File(currentValue + CatColor.valueOf(cat) + "_" + Wildcards.valueOf(wildCat) + ".png"));
            myButton.setIcon(new ImageIcon(image));
        }
        catch (IOException e)
        {
            System.out.println(currentValue + CatColor.valueOf(cat) + "_" + Wildcards.valueOf(wildCat) + ".png");
        }

        return myButton;
    }

    public JButton generateButtonFW(int cat, int levelSize, int wildcats)
    {
        JButton myButton = new JButton();
        String currentValue = pathName + (levelSize - 4) + "/";
        Image image;
        try
        {
            image = ImageIO.read(new File(currentValue + FootprintsColor.valueOf(cat) + "_" + Wildcards.valueOf(cat) + ".png"));
            myButton.setIcon(new ImageIcon(image));
        }
        catch (IOException e)
        {
            System.out.println(currentValue + FootprintsColor.valueOf(cat) + "_" + Wildcards.valueOf(cat) + ".png");
        }

        return myButton;
    }
}
