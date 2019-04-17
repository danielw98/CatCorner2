import javax.swing.*;
import java.util.Random;

public class CatBoard
{

    private JButton[] myCats;
    private JButton[] possibleCats;
    private JButton[] possibleWildCats;
    private JButton[] possibleFootprints;
    private JButton[] possibleWildFootprints;

    private int levelSize;
    private int levelCats;
    private int numberOfCats;
    private boolean isTurned;






    public CatBoard(int level, boolean wildCatOn, CatColors allCats)
    {


        myCats = new JButton[size * size];
        this.numberOfCats = numberOfCats;
        possibleCats = allCats.getPossibleCats(numberOfCats);
        possibleFootprints = getPossibleFootprints(numberOfCats);

        if(wildCatOn){

            possibleWildCats = allCats.getPossibleWildCats(numberOfCats);
            possibleWildFootprints = allCats.getPossibleWildFootprints(numberOfCats);
        }

        // in momentul de fata, stiu exact ce butoane pot sa folosesc
        initializeButtons(wildCatOn);
    }

    public JButton[] getBoard()
    {
        return myCats;
    }

    public void initializeButtons(boolean wildCatOn)
    {

        if(wildCatOn){

            initializeCatsW();
        }
        else {

            initializeCats();
        }
    }

    void initializeCats()
    {

            int randomCat;
            for(int i = 1; i <= myCats.length; i ++) {

                //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED
                randomCat = (int) (Math.random() * numberOfCats + 1);
                myCats[i] = possibleCats[randomCat];
            }
    }

    void initializeCatsW()
    {
        int randomCat;
        Random rand = new Random();
        boolean val;

        for(int i = 1; i <= myCats.length; i ++){

            val = rand.nextInt(20)==0; //probabilitate de 1/20
            randomCat = (int) (Math.random() * numberOfCats + 1);

            if(!val)
            {
                myCats[i] = possibleCats[randomCat];
            }
            else
            {
                myCats[i] = possibleWildCats[randomCat];
            }
        }
    }

}

