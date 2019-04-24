import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class CatBoard extends LevelsAttributes
{

    private JButton[] myCats;
    private JPanel currentBoard;

    private CatColors catColors;


    private byte levelSize;
    private byte numberOfCats;
    private byte wildCats;

    private int firstCatColor;
    private int secondCatColor;
    private CatCoordinates firstCatCoordinates;
    private CatCoordinates secondCatCoordinates;

    private PleaseHandle ph;
    public boolean hasChanged;
    private boolean levelFinished;
    private boolean[] marked;
    private int score;

    private CatCorner currentGame;

    public CatBoard(int level, byte wildCats, CatCorner currentGame, int score)
    {

        firstCatColor = 0;
        secondCatColor = 0;

        this.score = score;
        this.currentGame = currentGame;
        this.wildCats = wildCats;
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
        boolean isWild;
        Random r = new Random();
        String actionCommand;

        for(int i = 1; i < myCats.length; i ++) {


            //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED
            isWild = r.nextInt(20) == 5;
            randomCat = (int) (Math.random() * numberOfCats + 1);
            if(wildCats == 0 || !isWild)
            {

                 myCats[i] = catColors.generateButton(randomCat, levelSize);
                 actionCommand = i + "_" + randomCat + "_" + "cat";
            }
            else
            {

                int wildCat = (int) (Math.random() * wildCats + 1);
                myCats[i] = catColors.generateButtonW(randomCat, levelSize, wildCat);
                actionCommand = i + "_" + randomCat + "_" + "wildcat" + Wildcards.valueOf(wildCat);
            }

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
        System.out.println("X: " + X1 + " " + X2);
        System.out.println("Y: " + Y1 + " " + Y2);
        boolean isWild;
        Random r = new Random();
        int[] randomCat = new int[levelSize * levelSize + 1];
        String actionCommand;

        int comboSize = (X2 - X1 + 1) * (Y2 - Y1 + 1) + 1; // combinatie de 4 pisici - 5p; +1p pentru fiecare pisica suplimentara
        int catsUnflipped = 0; // punctajul initial pentru o pisica
        int comboScore = 0;
        for(int i = X1; i <= X2; i++)
        {
            for(int j = Y1; j <= Y2; j++)
            {

                //determinarea pozitiei
                position = CatCoordinates.getButtonIndex(i, j, levelSize);



                //calcularea scorului
                if(!marked[position])
                {
                    catsUnflipped += 5;
                    comboScore += catsUnflipped;
                }

                //reinitializarea butonului
                isWild = r.nextInt(20) == 5;
                randomCat[position] = (int) (Math.random() * numberOfCats + 1);

                if(wildCats == 0 || !isWild)
                {

                    myCats[position] = catColors.generateButtonF(randomCat[position], levelSize);
                    actionCommand = i + "_" + randomCat[position] + "_" + FootprintsColor.valueOf(randomCat[position]);
                    myCats[position].setActionCommand(actionCommand);
                    marked[position] = true;

                }
                else
                {

                    int wildCat = (int) (Math.random() * wildCats + 1);
                    myCats[position] = catColors.generateButtonFW(randomCat[position], levelSize, wildCat);
                    actionCommand = i + "_" + randomCat[position] + "_" + "footprint" + "_" + Wildcards.valueOf(wildCat);
                    myCats[position].setActionCommand(actionCommand);
                    marked[position] = true;

                }
            }
        }


        // daca este combinatie valida, actualizez scorul
        if(catsUnflipped != 0)
        {
            comboScore += comboSize;
            score += comboScore;
        }

        System.out.println("Combinatia: " + comboScore + "\t Total: " + score);
        updateBoard(randomCat);
        currentGame.setBoard(currentBoard);
        isLevelFinished();
    }

    void shuffle()
    {

       Random r = new Random();
       boolean isWild;
       int randomCat;
       String actionCommand;


        for(int i = 1; i < myCats.length; i++)
        {

            isWild = r.nextInt(20) == 5;

            if(!isWild)
            {

                randomCat = (int) (Math.random() * numberOfCats + 1);

                if(!marked[i])
                {
                    myCats[i] = catColors.generateButton(randomCat, levelSize);
                    actionCommand = i + "_" + randomCat + "_" + "cat";
                }
                else
                {
                    myCats[i] = catColors.generateButtonF(randomCat, levelSize);
                    actionCommand = i + "_" + randomCat + "_" + "footprint";
                }
            }
            else
            {
                randomCat = (int) (Math.random() * numberOfCats + 1);
                int wildCat = (int)(Math.random() * wildCats + 1);
                if(!marked[i])
                {
                    myCats[i] = catColors.generateButtonW(randomCat, levelSize, wildCat);
                    actionCommand = i + "_" + randomCat + "_" + "cat" + Wildcards.valueOf(wildCat);
                }
                else
                {
                    myCats[i] = catColors.generateButtonFW(randomCat, levelSize, wildCat);
                    actionCommand = i + "_" + randomCat + "_" + "footprint" + Wildcards.valueOf(wildCat);
                }
            }

            myCats[i].setActionCommand(actionCommand);
            myCats[i].addMouseListener(ph);
            myCats[i].setBorderPainted(false);
            currentBoard.add(myCats[i]);
        }
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

