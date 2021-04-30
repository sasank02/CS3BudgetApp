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
        Color color1 = Color.decode("#ffafbd");
        Color color2 = Color.decode("#ffc3a0");
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
    titleLabel.setBounds(100, 50, 400, 50);
    titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

    JLabel incomeLabel = new JLabel("Income");
    panel.add(incomeLabel);
    incomeLabel.setBounds(350, 110, 400, 50);
    incomeLabel.setFont(new Font("Helvetica", Font.BOLD, 19));


    JLabel salaryLabel = new JLabel("Salary: ", SwingConstants.CENTER);
    panel.add(salaryLabel);
    salaryLabel.setBounds(300, 155, 80, 30);
    salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

    JTextField monthlySalaryField = new JTextField();
    panel.add(monthlySalaryField);
    monthlySalaryField.setBounds(370, 160, 100, 20);
    monthlySalaryField.setBorder(null);
    monthlySalaryField.setEditable(false);
    monthlySalaryField.setBackground(new Color(242, 242, 242));
    monthlySalaryField.setHorizontalAlignment(JTextField.CENTER);

    JButton editMonthlySalaryField = new JButton("Edit");
    panel.add(editMonthlySalaryField);
    editMonthlySalaryField.setBounds(480, 160, 80, 20);
    editMonthlySalaryField.setBackground(new Color(0, 128, 0));
    editMonthlySalaryField.setForeground(Color.WHITE);
    editMonthlySalaryField.setBorder(null);

    editMonthlySalaryField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(monthlySalaryField.isEditable()) {
          monthlySalaryField.setEditable(false);
          monthlySalaryField.setBackground(new Color(242, 242, 242));
          editMonthlySalaryField.setText("Edit");
          String text = (String) monthlySalaryField.getText();
          monthlySalaryField.setText("$" + text);
        } else {
          monthlySalaryField.setEditable(true);
          monthlySalaryField.setBackground(Color.white);
          editMonthlySalaryField.setText("Lock");
        }
      }
    });

    JLabel otherSalaryLabel = new JLabel("Others: ", SwingConstants.CENTER);
    panel.add(otherSalaryLabel);
    otherSalaryLabel.setBounds(300, 205, 80, 30);
    otherSalaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

    JTextField otherSalaryField = new JTextField();
    panel.add(otherSalaryField);
    otherSalaryField.setBounds(370, 210, 100, 20);
    otherSalaryField.setBorder(null);
    otherSalaryField.setEditable(false);
    otherSalaryField.setBackground(new Color(242, 242, 242));
    otherSalaryField.setHorizontalAlignment(JTextField.CENTER);

    JButton editOtherSalaryField = new JButton("Edit");
    panel.add(editOtherSalaryField);
    editOtherSalaryField.setBounds(480, 210, 80, 20);
    editOtherSalaryField.setBackground(new Color(0, 128, 0));
    editOtherSalaryField.setForeground(Color.WHITE);
    editOtherSalaryField.setBorder(null);

    editOtherSalaryField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(otherSalaryField.isEditable()) {
          otherSalaryField.setEditable(false);
          otherSalaryField.setBackground(new Color(242, 242, 242));
          editOtherSalaryField.setText("Edit");
          String text = (String) otherSalaryField.getText();
          otherSalaryField.setText("$" + text);
        } else {
          otherSalaryField.setEditable(true);
          otherSalaryField.setBackground(Color.white);
          editOtherSalaryField.setText("Lock");
        }
      }
    });

    incomeButton = new JButton("Add Income");
    panel.add(incomeButton);
    incomeButton.setBounds(300, 255, 190, 30);
    customizeButton(incomeButton);

    incomeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    JLabel expensesDataLabel = new JLabel("Expense Types & Amounts");
    panel.add(expensesDataLabel);
    expensesDataLabel.setBounds(110, 320, 250, 30);
    expensesDataLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

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
    expenseLabel.setBounds(110, 110, 400, 50);
    expenseLabel.setFont(new Font("Helvetica", Font.BOLD, 19));

    JButton addNewExpense = new JButton("Add Expense");
    panel.add(addNewExpense);
    addNewExpense.setBounds(60, 155, 190, 30);
    customizeButton(addNewExpense);


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
    button.setBackground(Color.white);

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