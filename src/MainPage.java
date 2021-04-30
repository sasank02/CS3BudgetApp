import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPage {

    public MainPage() {
        JFrame frame = new JFrame("Budget App");
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel panel = new JPanel() {
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


        JLabel titleLabel = new JLabel("Budget Manager");
        panel.add(titleLabel);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN, 25));


        //MARK: EXPENSES
        JLabel expenseLabel = new JLabel("Expenses");
        panel.add(expenseLabel);
        expenseLabel.setBounds(110, 110, 400, 50);
        expenseLabel.setFont(new Font("Helvetica", Font.BOLD, 19));

        JButton addNewExpense = new JButton("Add Expense");
        panel.add(addNewExpense);
        addNewExpense.setBounds(60, 155, 190, 30);
        addNewExpense.setBorder(new RoundedBorder(30)); //10 is the radius
        addNewExpense.setBackground(Color.white);

        JButton viewSetExpensesButton = new JButton("Fixed Expenses");
        panel.add(viewSetExpensesButton);
        viewSetExpensesButton.setBounds(60, 205, 190, 30);
        viewSetExpensesButton.setBorder(new RoundedBorder(30)); //10 is the radius
        viewSetExpensesButton.setBackground(Color.white);

        JButton editRemoveExpenses = new JButton("Edit/Remove");
        panel.add(editRemoveExpenses);
        editRemoveExpenses.setBounds(60, 255, 190, 30);
        editRemoveExpenses.setBorder(new RoundedBorder(30)); //10 is the radius
        editRemoveExpenses.setBackground(Color.white);




        JLabel accountsLabel = new JLabel("Accounts: ");
        panel.add(accountsLabel);
        accountsLabel.setBounds(320, 180, 175, 30);
        accountsLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));

        JLabel incomeLabel = new JLabel("Income: ");
        panel.add(incomeLabel);
        incomeLabel.setBounds(320, 220, 175, 30);
        incomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));

        JLabel expensesLabel = new JLabel("Expenses: ");
        panel.add(expensesLabel);
        expensesLabel.setBounds(320, 260, 175, 30);
        expensesLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));

        JLabel expensesDataLabel = new JLabel("Expense Types & Amounts");
        panel.add(expensesDataLabel);
        expensesDataLabel.setBounds(110, 320, 250, 30);
        expensesDataLabel.setFont(new Font("Helvetica", Font.BOLD, 18));

        JButton daybydayButton = new JButton("Day by Day Breakdown");
        panel.add(daybydayButton);
        daybydayButton.setBounds(200, 570, 170, 30);
        customizeButton(daybydayButton);

        JButton cumulativeExpenseButton = new JButton("Cumulative Expense Breakdown");
        panel.add(cumulativeExpenseButton);
        cumulativeExpenseButton.setBounds(190, 610, 190, 30);
        customizeButton(cumulativeExpenseButton);
    }

    public void customizeButton(JButton button) {
        button.setBorder(null);
        button.setBackground(Color.black);
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
