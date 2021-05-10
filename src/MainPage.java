import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

  JPanel panel;
  JButton expenseButton;
  JButton incomeButton;
  JButton createExpenseButton;
  JButton manageExpenseButton;
  JButton settingsButton;
  JButton daybydayButton;
  JButton cumulativeExpenseButton;

  public MainPage() {
    JFrame frame = new JFrame("Budget App");
    frame.setSize(600, 700);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.decode("#02aab0");
        Color color2 = Color.decode("#00cdac");
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
      }

    };
    panel.setLayout(null);
    frame.add(panel);
    panel.setBackground(Color.blue);

    JLabel titleLabel = new JLabel("Expense Tracker and Budget Manager");
    panel.add(titleLabel);
    titleLabel.setForeground(Color.white);

    titleLabel.setBounds(100, 50, 400, 50);
    titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

    JLabel incomeLabel = new JLabel("Income");
    panel.add(incomeLabel);
    incomeLabel.setForeground(Color.white);

    incomeLabel.setBounds(350, 110, 400, 50);
    incomeLabel.setFont(new Font("Helvetica", Font.BOLD, 19));


    JLabel salaryLabel = new JLabel("Salary: ", SwingConstants.CENTER);
    panel.add(salaryLabel);
    salaryLabel.setForeground(Color.white);

    salaryLabel.setBounds(300, 155, 80, 30);
    salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

    JTextField monthlySalaryField = new JTextField();
    panel.add(monthlySalaryField);
    monthlySalaryField.setBounds(370, 160, 100, 20);
    monthlySalaryField.setBorder(null);

    JLabel otherSalaryLabel = new JLabel("Others: ", SwingConstants.CENTER);
    panel.add(otherSalaryLabel);
    otherSalaryLabel.setForeground(Color.white);

    otherSalaryLabel.setBounds(300, 205, 80, 30);
    otherSalaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

    JTextField otherSalaryField = new JTextField();
    panel.add(otherSalaryField);
    otherSalaryField.setBounds(370, 210, 100, 20);
    otherSalaryField.setBorder(null);

    incomeButton = new JButton("Add Income");
    panel.add(incomeButton);
    incomeButton.setBounds(300, 255, 190, 30);
    customizeButton(incomeButton);

    incomeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    daybydayButton = new JButton("Day by Day Breakdown");
    panel.add(daybydayButton);
    daybydayButton.setBounds(200, 570, 170, 30);
    customizeButton(daybydayButton);

    cumulativeExpenseButton = new JButton("Cumulative Expense Breakdown");
    panel.add(cumulativeExpenseButton);
    cumulativeExpenseButton.setBounds(190, 610, 190, 30);
    customizeButton(cumulativeExpenseButton);


    //MARK: EXPENSES
    JLabel expenseLabel = new JLabel("Expenses");
    panel.add(expenseLabel);
    expenseLabel.setForeground(Color.white);

    expenseLabel.setBounds(110, 110, 400, 50);
    expenseLabel.setFont(new Font("Helvetica", Font.BOLD, 19));

    JButton addNewExpense = new JButton("Add Expense");
    //comment
    panel.add(addNewExpense);

    addNewExpense.setBounds(60, 155, 190, 30);
    customizeButton(addNewExpense);

//comment
    JButton viewSetExpensesButton = new JButton("Fixed Expenses");
    panel.add(viewSetExpensesButton);
    viewSetExpensesButton.setBounds(60, 205, 190, 30);
    customizeButton(viewSetExpensesButton);

    JButton editRemoveExpenses = new JButton("Edit/Remove");
    panel.add(editRemoveExpenses);
    editRemoveExpenses.setBounds(60, 255, 190, 30);
    customizeButton(editRemoveExpenses);

  }

  public void customizeButton(JButton button) {
    button.setBorder(new RoundedBorder(30)); //10 is the radius
    button.setForeground(Color.white);

  }

  private static class RoundedBorder implements Border {

    private int radius;


    RoundedBorder(int radius) {
      this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
      return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
      return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
  }
}


