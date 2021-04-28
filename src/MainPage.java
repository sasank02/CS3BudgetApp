import javax.swing.*;
import java.awt.*;

public class MainPage {

    public MainPage() {
        JFrame frame = new JFrame("Budget App");
        frame.setSize(600, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        panel.setBackground(Color.white);

        JLabel titleLabel = new JLabel("Expense Tracker and Budget Manager");
        panel.add(titleLabel);
        titleLabel.setBounds(100, 50, 400, 50);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        JButton expenseButton = new JButton("Add Expense");
        panel.add(expenseButton);
        expenseButton.setBounds(200, 120, 90, 30);
        customizeButton(expenseButton);

        JButton incomeButton = new JButton("Add Income");
        panel.add(incomeButton);
        incomeButton.setBounds(300, 120, 90, 30);
        customizeButton(incomeButton);

        JButton createExpenseButton = new JButton("Create Expense Type");
        panel.add(createExpenseButton);
        createExpenseButton.setBounds(160, 180, 130, 30);
        customizeButton(createExpenseButton);

        JButton manageExpenseButton = new JButton("Manage Expense Types");
        panel.add(manageExpenseButton);
        manageExpenseButton.setBounds(155, 220, 140, 30);
        customizeButton(manageExpenseButton);

        JButton settingsButton = new JButton("Settings");
        panel.add(settingsButton);
        settingsButton.setBounds(160, 260, 130, 30);
        customizeButton(settingsButton);

        JLabel accountsLabel = new JLabel("Accounts: ");
        panel.add(accountsLabel);
        accountsLabel.setBounds(320, 180, 175, 30);
        accountsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));

        JLabel incomeLabel = new JLabel("Income: ");
        panel.add(incomeLabel);
        incomeLabel.setBounds(320, 220, 175, 30);
        incomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));

        JLabel expensesLabel = new JLabel("Expenses: ");
        panel.add(expensesLabel);
        expensesLabel.setBounds(320, 260, 175, 30);
        expensesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));

        JLabel expensesDataLabel = new JLabel("Expense Types & Amounts");
        panel.add(expensesDataLabel);
        expensesDataLabel.setBounds(110, 320, 250, 30);
        expensesDataLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

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
}
