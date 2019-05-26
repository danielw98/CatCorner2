import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class CatCorner extends JFrame
{

    private static JFrame frame;

    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel gameWindow;
    private JPanel mainPanel;
    private RightPanel rightPanel;

    private JLabel catCornerLabel;
    private JLabel instructions;

    private JLabel backgroundPanelN;
    private JLabel backgroundPanelS;
    private JLabel backgroundPanelE;
    private JLabel backgroundPanelW;

    private CatColors catColors;
    private CatBoard board;

    private byte level;
    private byte wildCats;
    private boolean gameOver;
    private int score;


    public CatCorner()
    {
        frame = new JFrame("Cat Corner");
        rightPanel = new RightPanel();


        initializeLabels();
        northPanel = new JPanel(new BorderLayout(3,3));
        southPanel = new JPanel(new BorderLayout(3,3));
        mainPanel  = new JPanel(new BorderLayout(3,3));
        gameWindow = new JPanel();


        northPanel.setBackground(Color.BLACK);
        southPanel.setBackground(Color.BLACK);
        mainPanel.setBackground(Color.BLACK);
        gameWindow.setBackground(Color.BLACK);

        northPanel.add(gameWindow, BorderLayout.CENTER);
        northPanel.add(rightPanel.getPanel(), BorderLayout.EAST);

        southPanel.add(catCornerLabel, BorderLayout.NORTH);
        southPanel.add(instructions, BorderLayout.SOUTH);


        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.setSize(new Dimension(780, 860));

        /*frame.add(backgroundPanelN, BorderLayout.NORTH);
        frame.add(backgroundPanelW, BorderLayout.WEST);
        frame.add(backgroundPanelE, BorderLayout.EAST);
        frame.add(backgroundPanelS, BorderLayout.SOUTH);*/
        frame.setBackground(Color.BLACK);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setMinimumSize(new Dimension(800, 900));
        frame.setSize(frame.getMinimumSize());
        frame.setResizable(true);
        //frame
        frame.setVisible(true);


        catColors = new CatColors();

        level = 1;
        wildCats = 0;
        score = 0;
        gameOver = false;

        board = new CatBoard(level, wildCats, this, score);

    }

    public void playGame()
    {

        playLevel();


            /*if(board.isLevelFinished())
            {
                System.out.println("afiseaza");
            }*/
           /* if(timeIsUp()) {
                gameOver = true;
            }*/
        //displayFinalScreen(score, level)
    }

    public void setBoard(JPanel currentBoard, int score)
    {

        northPanel.remove(gameWindow);
        System.out.println("se apeleaza set Board");
        gameWindow = new JPanel();
        gameWindow = currentBoard;

        northPanel.add(gameWindow, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void playLevel()
    {
        setGameWindow(level);
        rightPanel.setLevel(level);

        gameWindow.setSize(new Dimension(490, 490));
        gameWindow.add(board.getCurrentBoard());
        frame.add(mainPanel, BorderLayout.CENTER);

    }
    public void nextLevel(int score)
    {
        level++;
        if(level == 4 || level == 5 || level == 6 || level == 7)
        {
            wildCats++;
        }

        board = new CatBoard(level, wildCats, this, score);
        northPanel.remove(gameWindow);
        rightPanel.setLevel(level);
        System.out.println("se apeleaza");
        gameWindow = new JPanel();
        gameWindow = board.getCurrentBoard();
        northPanel.add(gameWindow, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    private void initializeLabels() {

        BufferedImage image;
        String path = "C:/Linux/proiecte_java/CatCorner/src/panels/";
        /*try {
            image = ImageIO.read(new File(path + "infoPanel.png"));
            infoPanel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            image = ImageIO.read(new File(path + "catCornerLabel.png"));
            catCornerLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File(path + "instructions.png"));
            instructions = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File(path + "backgroundPanelE.png"));
            backgroundPanelE = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File(path + "backgroundPanelN.png"));
            backgroundPanelN = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File(path + "backgroundPanelW.png"));
            backgroundPanelW = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File(path + "backgroundPanelS.png"));
            backgroundPanelS = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setScore(int score)
    {
        rightPanel.setScore(score);
    }



    private void setGameWindow(int size)
    {
        gameWindow.setLayout(new GridLayout(size, 0, 0, 0));
    }

    public static JFrame getFrame()
    {
        return frame;
    }

}