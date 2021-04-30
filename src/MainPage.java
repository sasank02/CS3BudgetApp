import javax.swing.*;
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

        expenseButton = new JButton("Add Expense");
        panel.add(expenseButton);
        expenseButton.setBounds(200, 120, 90, 30);
        customizeButton(expenseButton);

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JLabel incomeLabel = new JLabel("Income");
        panel.add(incomeLabel);
        incomeLabel.setBounds(340, 170, 100, 40);
        incomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel salaryLabel = new JLabel("Salary: ", SwingConstants.CENTER);
        panel.add(salaryLabel);
        salaryLabel.setBounds(300, 215, 80, 30);
        salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JTextField monthlySalaryField = new JTextField();
        panel.add(monthlySalaryField);
        monthlySalaryField.setBounds(370, 220, 100, 20);
        monthlySalaryField.setBorder(null);

        JLabel otherSalaryLabel = new JLabel("Others: ", SwingConstants.CENTER);
        panel.add(otherSalaryLabel);
        otherSalaryLabel.setBounds(300, 245, 80, 30);
        otherSalaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JTextField otherSalaryField = new JTextField();
        panel.add(otherSalaryField);
        otherSalaryField.setBounds(370, 250, 100, 20);
        otherSalaryField.setBorder(null);

        incomeButton = new JButton("Add Income");
        panel.add(incomeButton);
        incomeButton.setBounds(330, 280, 90, 30);
        customizeButton(incomeButton);

        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        settingsButton = new JButton("Settings");
        panel.add(settingsButton);
        settingsButton.setBounds(160, 260, 130, 30);
        customizeButton(settingsButton);

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
    }

    public void customizeButton(JButton button) {
        button.setBorder(null);
        button.setBackground(Color.black);
        button.setForeground(Color.white);
    }
}
