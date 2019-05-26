import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class RightPanel extends JPanel
{

    static int interval;
    static Timer timer;
    JPanel panel;
    JButton shuffle;
    JLabel time;
    JLabel score;
    JLabel level;
    JLabel top;

    public RightPanel()
  {
      panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBackground(Color.BLACK);

      initialize();
      addComponentsToPanel();

      new Stopwatch(this);
  }

  private void addComponentsToPanel()
    {
        panel.add(top);
        panel.add(level);
        panel.add(time);
        panel.add(score);
        panel.add(shuffle);
    }

  public JPanel getPanel()
  {
      return panel;
  }

  public void setTime(String text)
  {
      time.setText("                " + text);
  }

  public void setScore(int score)
  {
      this.score.setText("            " + score);
      panel.repaint();
  }
  public void setLevel(int level)
  {
      this.level.setText("          " + level);
      panel.repaint();
  }


  public void initialize()
  {

      Font font1 = new Font("SansSerif", Font.BOLD, 20);
      String pathName = "C:\\Linux\\proiecte_java\\CatCorner\\src\\panels\\";
      // shuffle
      shuffle = new JButton();
      Image image;
      try {
          Border emptyBorder = BorderFactory.createEmptyBorder();
          image = ImageIO.read(new File(pathName + "RightPanelSHUFFLE.png"));
          shuffle.setIcon(new ImageIcon(image));
          shuffle.setBorder(emptyBorder);
      } catch (IOException e) {
          e.printStackTrace();
      }


      // score
      score = new JLabel(new ImageIcon(pathName + "RightPanelSCORE.png"));
      score.setText("           SCORE HERE");
      score.setHorizontalTextPosition(JLabel.CENTER);
      score.setVerticalTextPosition(JLabel.CENTER);
      score.setFont(font1);


      //time
      time = new JLabel(new ImageIcon(pathName + "RightPanelTIME.png"));
      time.setText("                        TIME HERE");
      time.setHorizontalTextPosition(JLabel.CENTER);
      time.setVerticalTextPosition(JLabel.CENTER);
      time.setFont(font1);

      // level
      level = new JLabel(new ImageIcon(pathName + "RightPanelLEVEL.png"));
      level.setText("      LEVEL HERE");
      level.setHorizontalTextPosition(JLabel.CENTER);

      level.setVerticalTextPosition(JLabel.CENTER);
      level.setFont(font1);

      //top
      top = new JLabel(new ImageIcon(pathName + "RightPanelTOP.png"));

  }

}

class Stopwatch
{
    static int interval;
    static Timer timer;
    int START_TIME = 180;


    public Stopwatch(RightPanel rightPanel){

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = START_TIME;

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                int time = setInterval();
                String zero = "";
                if(time % 60 < 10)
                {
                    zero = "0";
                }
                String text = time / 60 + ":" + zero + time % 60;
                rightPanel.setTime(text);

            }
        }, delay, period);
    }

    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }

    public static void increaseTimer()
    {
        interval += 10;
    }


}