import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CatBoard extends LevelsAttributes
{

    private JButton[] myCats;
    private JPanel currentBoard;

    private CatColors catColors;


    private byte levelSize;
    private byte numberOfCats;

    private int firstCatColor;
    private int secondCatColor;
    private CatCoordinates firstCatCoordinates;
    private CatCoordinates secondCatCoordinates;

    private PleaseHandle ph;
    public boolean hasChanged;
    private boolean levelFinished;
    private boolean[] marked;

    private CatCorner currentGame;

    public CatBoard(int level, boolean wildCatOn, CatColors allCats, CatCorner currentGame)
    {

        firstCatColor = 0;
        secondCatColor = 0;

        this.currentGame = currentGame;

        LevelsAttributes levelsAttributes = new LevelsAttributes();

        levelSize = levelsAttributes.getLevelSize(level);

        myCats = new JButton[levelSize * levelSize + 1];

        catColors = new CatColors();
        currentBoard = new JPanel();

        currentBoard.setBackground(Color.BLACK);
        setGameWindow(levelSize);

        numberOfCats = levelsAttributes.getLevelCats(level);
        ph = new PleaseHandle();
        hasChanged = false;
        levelFinished = false;
        marked = new boolean[levelSize * levelSize + 1];

        initializeCats();

    }

    public void isValidMove()
    {

        int X1 = firstCatCoordinates.X;
        int Y1 = firstCatCoordinates.Y;
        int X2 = secondCatCoordinates.X;
        int Y2 = secondCatCoordinates.Y;
        int deltaX = X2-X1;
        int deltaY = Y2-Y1;
        if(deltaX == 0 || deltaY == 0)
        {
            return;
        }
        System.out.println("In interiorul isValidMove() - first si second");
        firstCatCoordinates.printPosition();
        secondCatCoordinates.printPosition();

        int cat3 = CatCoordinates.getButtonIndex(X2, Y1, levelSize);
        int cat4 = CatCoordinates.getButtonIndex(X1, Y2, levelSize);

        System.out.println("pozitiile cat3 si cat4: " + cat3 + " " + cat4);

        //values[0] - pozitia       values[1] - culoarea
        String temp3 = myCats[cat3].getActionCommand();
        String[] values3 = temp3.split("_");

        String temp4 = myCats[cat4].getActionCommand();
        String[] values4 = temp4.split("_");

        CatCoordinates thirdCatCoordinates = new CatCoordinates(cat3, levelSize);
        CatCoordinates fourthCatCoordinates = new CatCoordinates(cat4, levelSize);
        thirdCatCoordinates.printPosition();
        fourthCatCoordinates.printPosition();


        cat3 = Integer.parseInt(values3[1]);
        cat4 = Integer.parseInt(values4[1]);



        if(firstCatColor == secondCatColor && secondCatColor == cat3 && cat3 == cat4)
        {
            updateBoard(firstCatCoordinates.X, secondCatCoordinates.X , firstCatCoordinates.Y , secondCatCoordinates.Y);

        }
    }

    void initializeCats()
    {

        int randomCat;
        for(int i = 1; i < myCats.length; i ++) {

            //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED

            randomCat = (int) (Math.random() * numberOfCats + 1);
            String actionCommand = i + "_" + randomCat + "_" + "cat";
            myCats[i] = catColors.generateButton(randomCat, levelSize);
            myCats[i].setBackground(Color.BLACK);
            myCats[i].setActionCommand(actionCommand);
            myCats[i].addMouseListener(ph);
            myCats[i].setBorderPainted(false);
            currentBoard.add(myCats[i]);
        }
    }
    public void updateBoard(int[] randomCat)
    {

        currentBoard = new JPanel();
        currentBoard.setBackground(Color.BLACK);
        setGameWindow(levelSize);

        for(int i = 1; i < myCats.length; i++)
        {
            if(randomCat[i] != 0) {
                String actionCommand = i + "_" + randomCat[i] + "_" + "footprint";
                myCats[i].setActionCommand(actionCommand);
                myCats[i].setBackground(Color.BLACK);

                myCats[i].addMouseListener(ph);
                myCats[i].setBorderPainted(false);
            }
            currentBoard.add(myCats[i]);
        }
    }

    public JPanel getCurrentBoard() {

        return currentBoard;
    }

    void setGameWindow(int size)
    {
        currentBoard.setLayout(new GridLayout(0, size));
    }

    public int getLevelSize()
    {
        return levelSize;
    }
    public boolean isLevelFinished()
    {
        for (int i = 1; i < myCats.length && !levelFinished; i++)
        {
            if (!marked[i])
                return false;
        }
        System.out.println("**********GATA**********");
        currentGame.nextLevel();

        return true;
    }

    public void updateBoard(int X1, int X2, int Y1, int Y2)
    {
        int position;
        int incI =1, incJ=1;
        int temp;

        if(X1>X2)
        {
            temp = X1;
            X1 = X2;
            X2 = temp;

        }

        if(Y1>Y2)
        {
            temp = Y1;
            Y1 = Y2;
            Y2 = temp;
        }
        System.out.println("se apeleaza");
        System.out.println("X: " + X1 + " " + X2 + " " + incI);
        System.out.println("Y: " + Y1 + " " + Y2 + " " + incJ);
        int[] randomCat = new int[levelSize * levelSize + 1];
        for(int i = X1; i <= X2; i++)
        {
            for(int j = Y1; j <= Y2; j++)
            {
                System.out.println("inainte");
                position = CatCoordinates.getButtonIndex(i, j, levelSize);
                randomCat[position] = (int) (Math.random() * numberOfCats + 1);
                myCats[position] = catColors.generateButtonF(randomCat[position], levelSize);
                marked[position] = true;
                System.out.println("just marked" + position);
            }
        }
        updateBoard(randomCat);
        currentGame.setBoard(currentBoard);
        isLevelFinished();
    }



    class PleaseHandle extends MouseAdapter  // second class
    {

        private Component lastEntered;

        public void mousePressed(MouseEvent e)
        {

                JButton currentButton = (JButton) e.getSource();
                String temp = currentButton.getActionCommand();
                String[] values = temp.split("_");

                int position = Integer.parseInt(values[0]);
                firstCatColor = Integer.parseInt(values[1]);

                //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED
                firstCatCoordinates = new CatCoordinates(position, levelSize);
                secondCatColor = 0;
                secondCatCoordinates = null;
        }
        public void mouseEntered(MouseEvent e)
        {

            lastEntered = e.getComponent();
        }
        public void mouseClicked(MouseEvent e)
        {
            JButton current = (JButton) e.getSource();
            System.out.println("clicked " + current.getActionCommand());
        }
        public void mouseReleased(MouseEvent e)
        {
            JButton currentButton = (JButton) lastEntered;

            String temp = currentButton.getActionCommand();
            String[] values = temp.split("_");

            int position = Integer.parseInt(values[0]);
            secondCatColor = Integer.parseInt(values[1]);

            //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED
            secondCatCoordinates = new CatCoordinates(position, levelSize);

            isValidMove(); // daca miscarea nu este valida, functia nu face nimic
        }

    }


}

