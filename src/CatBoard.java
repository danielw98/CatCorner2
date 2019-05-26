import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PipedOutputStream;
import java.util.Random;


public class CatBoard
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

        if(firstCatColor == 10) // am atribuit 10 pentru RAINBOW_CAT
        {
            firstCatColor = secondCatColor;
        }
        if(secondCatColor == 10)
        {
            secondCatColor = cat3; // este suficient sa fie identica cu una din celelalte pisici
        }
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
            actionCommand = i + "_" + randomCat + "_" + "cat";

            if(wildCats == 0 || !isWild)
            {

                 myCats[i] = catColors.generateButton(randomCat, levelSize);
            }
            else
            {

                int wildCat = (int) (Math.random() * wildCats + 1);
                myCats[i] = catColors.generateButtonW(randomCat, levelSize, wildCat);
                actionCommand += "_" + Wildcards.valueOf(wildCat);
            }

            myCats[i].setBackground(Color.BLACK);
            myCats[i].setActionCommand(actionCommand);
            myCats[i].addMouseListener(ph);
            myCats[i].setBorderPainted(false);
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
        currentGame.nextLevel(score);

        return true;
    }

    public void updateBoard(int X1, int X2, int Y1, int Y2)
    {

        //ordonare
        int position;
        int temp;

        if(X1>X2) {

            temp = X1;
            X1 = X2;
            X2 = temp;
        }
        if(Y1>Y2) {

            temp = Y1;
            Y1 = Y2;
            Y2 = temp;
        }

        System.out.println("se apeleaza");
        System.out.println("X: " + X1 + " " + X2);
        System.out.println("Y: " + Y1 + " " + Y2);


        //initializare variabile
        boolean isWild;
        Random r = new Random();
        int[] randomCat = new int[levelSize * levelSize + 1];
        int[] wild = new int[levelSize * levelSize + 1];

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

                if(wildCats == 0 || !isWild) {

                    myCats[position] = catColors.generateButtonF(randomCat[position], levelSize);
                    marked[position] = true;
                }
                else {

                    wild[position] = (int) (Math.random() * wildCats + 1);
                    myCats[position] = catColors.generateButtonFW(randomCat[position], levelSize, wild[position]);
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
        currentGame.setScore(score);

        String actionCommand;

        //refresh board
        currentBoard = new JPanel();
        currentBoard.setBackground(Color.BLACK);
        setGameWindow(levelSize);

        for(int i = 1; i < myCats.length; i++)
        {
            if(randomCat[i] != 0)
            {
                actionCommand = i + "_" + randomCat[i] + "_" + "footprint";
               if(wild[i] != 0)
                {
                    actionCommand += "_" + Wildcards.valueOf(wild[i]);
                }
                myCats[i].setActionCommand(actionCommand);
                myCats[i].setBackground(Color.BLACK);

                myCats[i].addMouseListener(ph);
                myCats[i].setBorderPainted(false);
            }
            currentBoard.add(myCats[i]);
        }


        currentGame.setBoard(currentBoard, score);
        isLevelFinished();
    }

    public void shuffle()
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
                    actionCommand = i + "_" + randomCat + "_" + "cat" + "_" + Wildcards.valueOf(wildCat);
                }
                else
                {
                    myCats[i] = catColors.generateButtonFW(randomCat, levelSize, wildCat);
                    actionCommand = i + "_" + randomCat + "_" + "footprint" + "_" + Wildcards.valueOf(wildCat);
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



        //coltul stanga sus
        int height;
        int width;
        JFrame tempFrame;
        JFrame oldFrame;
        Point start;
        Point end;
        boolean isPressed;
        MyCanvas c;

        public void mousePressed(MouseEvent e)
        {

                JButton currentButton = (JButton) e.getSource();
                String temp = currentButton.getActionCommand();
                String[] values = temp.split("_");



                // determin dimensiunea unui buton, precum si
                height = currentButton.getHeight();
                width = currentButton.getWidth();
                start = currentButton.getLocationOnScreen();
                oldFrame = CatCorner.getFrame();

                int position = Integer.parseInt(values[0]);
                firstCatColor = Integer.parseInt(values[1]);

                if(values.length >= 4 && values[3].equals("RAINBOW"))
                {
                    firstCatColor = 10;
                }

                //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED
                firstCatCoordinates = new CatCoordinates(position, levelSize);
                secondCatColor = 0;
                secondCatCoordinates = null;

                isPressed = true;

        }
        public void mouseEntered(MouseEvent e)
        {
            lastEntered = (JButton) e.getSource();

            if(isPressed) {
                end = lastEntered.getLocationOnScreen();

                tempFrame = oldFrame;

                c = new MyCanvas(start, end, width, height);
                c.setBackground(Color.RED);
               // tempFrame.getContentPane().add(c);
                //tempFrame.setVisible(true);
               // CatCorner.setFrame(tempFrame);
            }
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

            if(values.length >= 4 && values[3].equals("RAINBOW"))
            {
                secondCatColor = 10;
            }
            //1=PURPLE  2=YELLOW    3=GREEN     4=ORANGE    5=RED

            secondCatCoordinates = new CatCoordinates(position, levelSize);

//            tempFrame.getContentPane().remove(c);
            tempFrame.setVisible(true);
            isPressed = false;

            //CatCorner.setFrame(oldFrame);
            isValidMove(); // daca miscarea nu este valida, functia nu face nimic
        }


    }


}

class MyCanvas extends JComponent {

    private int x;
    private int y;
    private int width;
    private int height;


    public MyCanvas(Point start, Point end, int width, int height)
    {

        // se apeleaza;
        x = (int) start.getX() / width * width;
        y = (int) start.getY() / height * height;

        int tempX = (int) end.getX() / width  * width;
        int tempY = (int) end.getY() / height * height;

        this.width = tempX - x;
        this.height = tempY - y;

        System.out.println(x + " " + y + " " + this.width + " " + this.height);
    }


    /*public MyCanvas2(int x, int y, int width, int height)
    {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }*/

    public void paint(Graphics g) {


        for(int i = 0; i < 5; i++)
        {
            g.drawRect(x+i, y+i, width-2*i, height-2*i);
        }
    }
}

