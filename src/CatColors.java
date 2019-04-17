import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// clasa care adreseaza lucrul cu imaginile. creez vectori de vectori de butoane care pot fi folosite

public class CatColors
{


    private JButton[][] catButtons; //fata
    private JButton[][] footprintButtons; //dos
    private JButton[][] wCatButtons; //fata wildcards - de la 3 in sus
    private JButton[][] wFootprintButtons; //dos wildcards

    private final int MAX_SIZE = 6;
    private final int WILDCARDS = 4;


    FootprintsColor footprintColors; //
    CatColor catColors; // 5 culori posibile
    CatSizes catSizes; // 6 dimensiuni posibile
    Wildcards wildcards; // 4 wildcard-uri posibile

    LevelsAttributes levelAttributes;

    CatColors()
    {
        buildCats();
    }

    public void buildCats()
    {

        catButtons = new JButton[MAX_SIZE + 1][]; // vreau sa pot folosi indexul 6
        footprintButtons = new JButton[MAX_SIZE + 1][];  //vectorii de butoane contin imaginile pentru fiecare buton
        wCatButtons = new JButton[MAX_SIZE + 1][];
        wFootprintButtons = new JButton[MAX_SIZE + 1][];


        // de la mare la mic
        for(int i = 1; i <= MAX_SIZE; i ++){
            createCatsButtons(i);
            createFootprintButtons(i);


            if(i >= 4)
            {
                createCatsButtonsW(i);
                createFootprintButtonsW(i);
            }
        }
    }

    public void createCatsButtonsW(int i)
    {

        BufferedImage image;
        String pathName = " C:\\Linux\\proiecte_java\\CatCorner\\src\\resources\\" + i + "_";

        //functia determina numele fisierelor si adauga icoanele la butoane
        for(int j = 1; j <= catButtons[i].length; j++)
        {
            try {
                image = ImageIO.read(new File(pathName + CatColor.valueOf(j) + ".png"));
                catButtons[i][j].setIcon(new ImageIcon(image));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void createFootprintButtonsW(int i)
    {
        BufferedImage image;
        String pathName = " C:\\Linux\\proiecte_java\\CatCorner\\src\\resources\\" + i + "_";

        //functia determina numele fisierelor si adauga icoanele la butoane
        for(int j = 1; j <= footprintButtons[i].length; j++)
        {
            for(int k = 1; k <= WILDCARDS; k ++)
            try {
                image = ImageIO.read(new File(pathName + FootprintsColor.valueOf(j) + ".png"));
                footprintButtons[i][j].setIcon(new ImageIcon(image));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createFootprintButtons(int i)
    {
        BufferedImage image;
        String pathName = " C:\\Linux\\proiecte_java\\CatCorner\\src\\resources\\" + i + "_";

        //functia determina numele fisierelor si adauga icoanele la butoane
        for(int j = 1; j <= footprintButtons[i].length; j++)
        {
            try {
                image = ImageIO.read(new File(pathName + FootprintsColor.valueOf(j) + ".png"));
                footprintButtons[i][j].setIcon(new ImageIcon(image));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCatsButtons(int i)
    {

        BufferedImage image;
        String pathName = " C:\\Linux\\proiecte_java\\CatCorner\\src\\resources\\" + i + "_";

        //functia determina numele fisierelor si adauga icoanele la butoane
        for(int j = 1; j <= catButtons[i].length; j++)
        {
            try {
                image = ImageIO.read(new File(pathName + CatColor.valueOf(j) + ".png"));
                catButtons[i][j].setIcon(new ImageIcon(image));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}