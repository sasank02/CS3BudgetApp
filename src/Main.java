import javax.swing.*;
import java.awt.*;

public class Main {

  public static void main(String[] args){
    JFrame frame = new JFrame("Budget App"); //dffd
    frame.setSize(400, 500);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(null);
    frame.add(panel);
    panel.setBackground(Color.white);
  }


}