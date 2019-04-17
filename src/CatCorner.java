import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class CatCorner extends JFrame
{

    private final int MAX_LEVEL = 20;
    private JFrame frame;

    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel gameWindow;
    private JPanel mainPanel;

    private JLabel infoPanel;
    private JLabel catCornerLabel;
    private JLabel instructions;
    private JLabel backgroundPanelNS;
    private JLabel backgroundPanelEW;

    private CatColors catColors;

    private byte level;



    private boolean wildCatsOn;
    private boolean gameOver;

    public CatCorner()
    {
        frame = new JFrame("Cat Corner");

        initializeLabels();

        northPanel = new JPanel(new BorderLayout(3,3));
        southPanel = new JPanel(new BorderLayout(3,3));
        mainPanel  = new JPanel(new BorderLayout(3,3));
        gameWindow = new JPanel(new GridLayout(10, 10));

        northPanel.setBackground(Color.BLACK);
        southPanel.setBackground(Color.BLACK);
        mainPanel.setBackground(Color.BLACK);


        northPanel.add(gameWindow, BorderLayout.CENTER);
        northPanel.add(infoPanel, BorderLayout.EAST);
        southPanel.add(catCornerLabel, BorderLayout.NORTH);
        southPanel.add(instructions, BorderLayout.SOUTH);


        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.CENTER);
        mainPanel.setSize(new Dimension(780, 860));

        //frame.add(backgroundPanelNS, BorderLayout.NORTH);
        //frame.add(backgroundPanelEW, BorderLayout.WEST);
        //frame.add(backgroundPanelEW, BorderLayout.EAST);
       // frame.add(backgroundPanelNS, BorderLayout.SOUTH);
        frame.setBackground(Color.BLACK);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setMinimumSize(new Dimension(790, 870));
        frame.setSize(frame.getMinimumSize());
        //frame.setResizable(false);
        //frame
        frame.setVisible(true);



        level = 1;
        wildCatsOn = false;
        gameOver = false;

        playGame();
    }

    void playGame()
    {
        while(level <= MAX_LEVEL && !gameOver) {
            CatBoard board = new CatBoard(level, wildCatsOn, catColors); // vreau sa imi pregateasca tabla

            //board.playLevel();

            if(level == 5) {
                wildCatsOn = true;
            }
            level++;
            /*if(timeIsUp()) {
                gameOver = true;
            }*/
        }
        //displayFinalScreen(score, level)
    }


    void initializeLabels() {

        BufferedImage image;
        try {
            image = ImageIO.read(new File("C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\infoPanel.png"));
            infoPanel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File("C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\catCornerLabel.png"));
            catCornerLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File("C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\instructions.png"));
            instructions = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File("C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\backgroundPanelEW.png"));
            backgroundPanelEW = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(new File("C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\backgroundPanelNS.png"));
            backgroundPanelNS = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
