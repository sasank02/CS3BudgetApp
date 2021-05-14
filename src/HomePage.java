
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HomePage {

  JPanel panel;
  JButton addIncomeButton;
  JButton withdrawButton;
  JButton addCategoryButton;
  JButton cancelACategoryButton;
  JTextField incomeAmountField;
  JTextField otherSalaryField;
  JTextField withdrawAmountField;
  JButton askEditButton;

  LinkedList<Category> categories = new LinkedList<Category>();  //linked list data structure is used instead of arraylist
  ArrayList<JLabel> categoriesLabels = new ArrayList<JLabel>();
  JComboBox<String> categoriesComboBox = new JComboBox<String>(); // this is the drop down for withdrawing a category

  Double totalAmountLeft = 0.0;
  JLabel totalAmountLeftLabel = new JLabel("Amount Left: $" + totalAmountLeft);
  JCheckBox totalAmountLeftCheckBox = new JCheckBox("+ Amount Left");

  JScrollPane categoriesScrollPane = new JScrollPane();
  JPanel categoriesPanel = new JPanel();

  JScrollPane recentTransactionsPane = new JScrollPane();
  JPanel recentTransactionsPanel = new JPanel();
  Stack<Ticket> recentTransStack = new Stack<Ticket>();
  ArrayList<JLabel> recentTransLabels = new ArrayList<JLabel>();

  Timer timer; // used for keeping track of the current time which would be used to compare with the timed categories

  public HomePage() {
    setGraphics();

    panel.add(totalAmountLeftLabel);
    totalAmountLeftLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
    totalAmountLeftLabel.setBounds(30, 610, 200, 40);

    categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.PAGE_AXIS));
    categoriesScrollPane.add(categoriesPanel);
    categoriesPanel.setPreferredSize(new Dimension(400, 400));
    categoriesPanel.revalidate();
    categoriesPanel.repaint();

    panel.add(categoriesScrollPane);
    categoriesScrollPane.setBounds(30, 200, 240, 400);
    categoriesScrollPane.setViewportView(categoriesPanel);
    categoriesScrollPane.validate();
    categoriesScrollPane.getVerticalScrollBar().setUnitIncrement(10);

    panel.add(categoriesComboBox);
    categoriesComboBox.setBounds(370, 505, 120,20);
    categoriesComboBox.setBorder(null);

    recentTransactionsPanel.setLayout(new BoxLayout(recentTransactionsPanel, BoxLayout.Y_AXIS));
    recentTransactionsPane.add(recentTransactionsPanel);
    recentTransactionsPanel.revalidate();
    recentTransactionsPanel.repaint();
    recentTransactionsPanel.setPreferredSize(new Dimension(240, 400));


    panel.add(recentTransactionsPane);
    recentTransactionsPane.setBounds(625, 200, 240, 400);
    recentTransactionsPane.setViewportView(recentTransactionsPanel);
    recentTransactionsPane.validate();
    recentTransactionsPane.getVerticalScrollBar().setUnitIncrement(10);

    panel.add(totalAmountLeftCheckBox);
    totalAmountLeftCheckBox.setBounds(475, 455, 125, 30);

  }


  public void setListeners(){
    addCategoryButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField categoryName = new JTextField();
        JTextField categoryRank = new JTextField();
        JTextField categoryExistingAmount = new JTextField();
        JTextField categoryAmountNeeded = new JTextField();
        JTextField categoryDeadline = new JTextField();
        categoryDeadline.setEditable(false);

        JRadioButton regularyCategoryButton = new JRadioButton("Regular Category");
        JRadioButton timedCategoryButton = new JRadioButton("Timed Category");
        ButtonGroup group = new ButtonGroup();
        group.add(regularyCategoryButton);
        group.add(timedCategoryButton);
        regularyCategoryButton.setSelected(true);

        regularyCategoryButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            categoryDeadline.setText("");
            categoryDeadline.setEditable(false);
          }
        });

        timedCategoryButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            categoryDeadline.setEditable(true);
          }
        });

        Object[] fields = {"Category Name:", categoryName , "Category Rank:", categoryRank, "Existing Amount:" , categoryExistingAmount, "Amount Needed:", categoryAmountNeeded, "Category Type:", regularyCategoryButton, timedCategoryButton, "Category Deadline (yyyy/MM/dd HH:mm:ss):", categoryDeadline};
        int input = JOptionPane.showConfirmDialog(null, fields, "New Category", JOptionPane.OK_CANCEL_OPTION);

        if(input == 0) {
          Category newCategory = null;

          if(regularyCategoryButton.isSelected()) {
            newCategory = new Category(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()));
            String toPlace = ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" + newCategory.existingAmount + "/$" + newCategory.neededAmount;

            JLabel newCategoryLabel = new JLabel(toPlace, SwingConstants.CENTER); //User can see the update in categories list
            newCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            categoriesLabels.add(newCategory.weight - 1, newCategoryLabel);
          } else if(timedCategoryButton.isSelected()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date deadLine = null;
            try {
              deadLine = (Date) dateFormat.parse((String) categoryDeadline.getText());
            } catch (ParseException parseException) {
              parseException.printStackTrace();
            }

            newCategory = new TimedCategory(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()), deadLine);

            String categoryInfo = " " + ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" + newCategory.existingAmount + "/$" + newCategory.neededAmount + "  Deadline: " + categoryDeadline.getText();
            String info1 = categoryInfo.substring(0, categoryInfo.indexOf("  Deadline: "));
            String info2 = categoryInfo.substring(categoryInfo.indexOf("  Deadline: "));
            JLabel newCategoryLabel = new JLabel("<html>" + info1 + "<br/>" + info2 + "</html>");
            newCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            categoriesLabels.add(newCategory.weight - 1, newCategoryLabel);
          }

          categories.add(newCategory.weight - 1, newCategory);

          addCategoriesInScrollPane();

          if(totalAmountLeft > 0.0) {
            totalAmountLeft = updateCategoryList(totalAmountLeft);
          }

          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);

          updateCategoryRank(); // updates the rank

          categoriesPanel.revalidate();
          categoriesPanel.repaint();

          updateWithdrawCategoryDropDownMenu();

          //DATE
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          Date date = new Date();
          recentTransStack.push(new Ticket("New Category", 0.0 ,  dateFormat.format(date)));
        }
      }
    });

    cancelACategoryButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComboBox<String> categoriesList = new JComboBox<String>();
        for(int i = 0; i < categories.size(); i++) {
          categoriesList.addItem(categories.get(i).title);
        }

        int input = JOptionPane.showConfirmDialog(null, categoriesList, "Category Cancellation", JOptionPane.OK_CANCEL_OPTION);

        if(input == 0) {
          categories.remove(categoriesList.getSelectedIndex());
          categoriesLabels.remove(categoriesList.getSelectedIndex());
          categoriesComboBox.removeItemAt(categoriesList.getSelectedIndex());
          addCategoriesInScrollPane();
          updateCategoryRank();
          categoriesPanel.revalidate();
          categoriesPanel.repaint();
        }
      }
    });

    addIncomeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        incomeAmountField.setEditable(false);
        otherSalaryField.setEditable(false);

        String amountString = (String) incomeAmountField.getText();
        String otherSalaryString = (String) otherSalaryField.getText();

        Double amount = 0.0;
        Double otherSalary = 0.0;

        if(amountString.length() > 0) {
          amount = Double.parseDouble(amountString);
        }

        if(otherSalaryString.length() > 0) {
          otherSalary = Double.parseDouble(otherSalaryString);
        }

        int oldCategoriesListSize = categories.size();

        if(amount > 0.0 || otherSalary > 0.0) {
          Double totalAmount = amount + otherSalary + totalAmountLeft;
          totalAmountLeft = 0.0;

          //ADD MONEY TO CATEGORY SPREAD EVENLY
          totalAmountLeft += updateCategoryList(totalAmount);
          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);
          updateCategoryRank();

          if(categories.size() != oldCategoriesListSize) {
            updateWithdrawCategoryDropDownMenu();
          }

          //DATE
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          Date date = new Date();

          recentTransStack.push(new Ticket("Income", amount+otherSalary ,  dateFormat.format(date)));
        }
      }
    });

    withdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        withdrawAmountField.setEditable(false);

        String amountString = (String) withdrawAmountField.getText();
        String sourceString = (String) categoriesComboBox.getSelectedItem();

        Double doubleTotalAmount = Double.parseDouble(amountString);

        //Remove MONEY From CATEGORY SPREAD EVENLY

        int categoryIndex = findCategory(sourceString);

        if(categoryIndex >= 0) {
          categories.get(categoryIndex).existingAmount -= doubleTotalAmount;
          if(totalAmountLeftCheckBox.isSelected()) {
            totalAmountLeft += doubleTotalAmount;
            totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);
          }


          //DATE
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          Date date = new Date();

          recentTransStack.push(new Ticket("Withdrawal", doubleTotalAmount ,  dateFormat.format(date)));


          categoriesLabels.get(categoryIndex).setText(" " + ((Integer) categories.get(categoryIndex).weight) + ") " + categories.get(categoryIndex).title.toUpperCase() + "  $" + categories.get(categoryIndex).existingAmount + "/$"  + categories.get(categoryIndex).neededAmount);
          categoriesPanel.revalidate();
          categoriesPanel.repaint();
        } else {
          JOptionPane.showMessageDialog(null, "This category doesn't exist", "Alert", JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    askEditButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        if(incomeAmountField.isEditable()){
          incomeAmountField.setEditable(false);
          otherSalaryField.setEditable(false);
        }
        else{
          incomeAmountField.setEditable(true);
          otherSalaryField.setEditable(true);
        }

      }
    });

  }

  public int findCategory(String src){
    for(int i = 0; i < categories.size(); i++) {
      if(categories.get(i).title.toUpperCase().equals(src.toUpperCase())) {
        return i;
      }
    }

    return -1;
  }

  public void setGraphics() {
    JFrame frame = new JFrame("Budget");
    frame.setSize(900, 700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

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

    JLabel titleLabel = new JLabel("Budget Manager");
    titleLabel.setForeground(Color.white);
    titleLabel.setBounds(320, 40, 400, 50);
    titleLabel.setFont(new Font("Heveltica", Font.BOLD, 25));

    JLabel categoriesLabel = new JLabel("Categories");
    categoriesLabel.setForeground(Color.white);
    categoriesLabel.setBounds(60, 100, 400, 50);
    categoriesLabel.setFont(new Font("Heveltica", Font.BOLD, 18));

    JLabel recentTransactionsLabel = new JLabel("Recent Actions");
    recentTransactionsLabel.setForeground(Color.white);
    recentTransactionsLabel.setBounds(665, 100, 400, 50);
    recentTransactionsLabel.setFont(new Font("Heveltica", Font.BOLD, 18));

    String text = "All your recent incomes," + "<br>" +
      "withdrawals, and actions are here" + "<br>" + "<br>";
    JLabel recentTransactionsSubtitleLabel = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
    recentTransactionsSubtitleLabel.setForeground(Color.white);
    recentTransactionsSubtitleLabel.setBounds(620, 145, 400, 50);
    recentTransactionsSubtitleLabel.setFont(new Font("Heveltica", Font.PLAIN, 14));

    JLabel addIncomeLabel = new JLabel("Add Income");
    addIncomeLabel.setForeground(Color.white);
    addIncomeLabel.setBounds(370, 100, 400, 50);
    addIncomeLabel.setFont(new Font("Heveltica", Font.BOLD, 18));

    JLabel incomeAmountLabel = new JLabel("Salary: ", SwingConstants.CENTER);
    incomeAmountLabel.setForeground(Color.white);
    incomeAmountLabel.setBounds(300, 185, 80, 30);
    incomeAmountLabel.setFont(new Font("Heveltica", Font.PLAIN, 15));

    incomeAmountField = new JTextField();
    incomeAmountField.setBounds(370, 190, 150, 20);
    incomeAmountField.setBorder(null);
    incomeAmountField.setBorder( new MatteBorder(0, 0, 2, 0, Color.white));
    incomeAmountField.setBackground(new Color(0, 0, 0, 0));
    incomeAmountField.setEditable(false);

    JLabel otherSalaryLabel = new JLabel("Others: ", SwingConstants.CENTER);
    otherSalaryLabel.setForeground(Color.white);
    otherSalaryLabel.setBounds(300, 235, 80, 30);
    otherSalaryLabel.setFont(new Font("Heveltica", Font.PLAIN, 15));

    otherSalaryField = new JTextField();
    otherSalaryField.setBounds(370, 240, 150, 20);
    otherSalaryField.setBorder(null);
    otherSalaryField.setBorder( new MatteBorder(0, 0, 2, 0, Color.white));
    otherSalaryField.setBackground(new Color(0, 0, 0, 0));
    otherSalaryField.setEditable(false);

    addIncomeButton = new JButton("Add Income");
    addIncomeButton.setBounds(350, 290, 150, 30);
    customizeButton(addIncomeButton);

    askEditButton = new JButton("Edit");
    askEditButton.setBounds(370, 150, 115, 22);
    askEditButton.setBorder(new RoundedBorder(22)); //10 is the radius
    askEditButton.setForeground(Color.white);
    panel.add(askEditButton);
    customizeButton(askEditButton);

    //WITHDRAW
    JLabel withdrawLabel = new JLabel("Withdraw");
    withdrawLabel.setForeground(Color.white);
    withdrawLabel.setBounds(380, 365, 400, 50);
    withdrawLabel.setFont(new Font("Heveltica", Font.BOLD, 18));
    //commit
    JLabel withdrawAmountLabel = new JLabel("Amount: ", SwingConstants.CENTER);
    withdrawAmountLabel.setForeground(Color.white);
    withdrawAmountLabel.setBounds(297, 450, 80, 30);
    withdrawAmountLabel.setFont(new Font("Heveltica", Font.PLAIN, 15));

    withdrawAmountField = new JTextField();
    withdrawAmountField.setBounds(370, 455, 100, 20);
    withdrawAmountField.setBorder(null);
    withdrawAmountField.setBorder( new MatteBorder(0, 0, 2, 0, Color.white));
    withdrawAmountField.setBackground(new Color(0, 0, 0, 0));

    JLabel withdrawSourceLabel = new JLabel("Category: ", SwingConstants.CENTER);
    withdrawSourceLabel.setForeground(Color.white);
    withdrawSourceLabel.setBounds(297, 500, 80, 30);
    withdrawSourceLabel.setFont(new Font("Heveltica", Font.PLAIN, 15));

    withdrawButton = new JButton("Withdraw");
    withdrawButton.setBounds(350, 555, 150, 30);
    customizeButton(withdrawButton);
    withdrawAmountField.setEditable(false);

    JButton askWithdrawButton = new JButton("Edit");
    askWithdrawButton.setBounds(370, 415, 115, 22);
    askWithdrawButton.setBorder(new RoundedBorder(22)); //10 is the radius
    askWithdrawButton.setForeground(Color.white);
    panel.add(askWithdrawButton);
    customizeButton(askWithdrawButton);

    askWithdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        if(withdrawAmountField.isEditable()){
          withdrawAmountField.setEditable(false);
        }
        else{
          withdrawAmountField.setEditable(true);
        }

      }
    });


    addCategoryButton = new JButton("Add Category");
    addCategoryButton.setBounds(10, 150, 150, 30);
    addCategoryButton.setBorder(new RoundedBorder(30)); //10 is the radius
    addCategoryButton.setForeground(Color.white);
    panel.add(addCategoryButton);
    customizeButton(addCategoryButton);

    cancelACategoryButton = new JButton("Cancel Category");
    cancelACategoryButton.setBounds(165, 150, 165, 30);
    cancelACategoryButton.setBorder(new RoundedBorder(30)); //10 is the radius
    cancelACategoryButton.setForeground(Color.white);
    panel.add(cancelACategoryButton);
    customizeButton(cancelACategoryButton);

//comment
    //comment

    panel.add(withdrawAmountLabel);
    panel.add(withdrawAmountField);
    panel.add(withdrawSourceLabel);
    panel.add(withdrawButton);
    panel.add(recentTransactionsLabel);
    panel.add(recentTransactionsSubtitleLabel);


    panel.add(addIncomeButton);
    panel.add(incomeAmountLabel);
    panel.add(incomeAmountField);
    panel.add(otherSalaryLabel);
    panel.add(otherSalaryField);
    panel.add(titleLabel);
    panel.add(categoriesLabel);
    panel.add(addIncomeLabel);
    panel.add(withdrawLabel);
    frame.setVisible(true);

    setListeners();

    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        categoryDeadlineCheck();
      }
    });

    timer.start();
  }

  public void customizeButton(JButton button) {
    button.setBorder(new RoundedBorder(30)); //10 is the radius
    button.setContentAreaFilled(false);
  }

  public void updateCategoryRank() {
    //assigns new rank for each category if categories are removed
    for (int i = 0; i < categories.size(); i++) {
      categories.get(i).weight = i + 1;
      Category category = categories.get(i);
      if(categories.get(i) instanceof TimedCategory) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String categoryInfo = " " + ((Integer) category.weight) + ") " + category.title.toUpperCase() + "  $" + category.existingAmount + "/$" + category.neededAmount + "  Deadline: " + dateFormat.format(((TimedCategory) categories.get(i)).deadline);
        String info1 = categoryInfo.substring(0, categoryInfo.indexOf("  Deadline: "));
        String info2 = categoryInfo.substring(categoryInfo.indexOf("  Deadline: "));
        categoriesLabels.get(i).setText("<html>" + info1 + "<br/>" + info2 + "</html>");
      } else {
        categoriesLabels.get(i).setText(((Integer) category.weight) + ") " + category.title.toUpperCase() + "  $" + category.existingAmount + "/$" + category.neededAmount);
      }
    }
  }

  public Double updateCategoryList(Double totalAmount) {
    int i = 0;
    while(i < categories.size()) {
      if(totalAmount > 0.0) {
        Category category = categories.get(i);
        double amountLeftToPay = category.neededAmount - category.existingAmount;
        if(category instanceof TimedCategory == false || (category instanceof TimedCategory && ((TimedCategory) categories.get(i)).deadlinePassed == false)) {
          if (totalAmount < amountLeftToPay) {
            category.existingAmount += totalAmount;
            totalAmount = 0.0;
          } else if (totalAmount >= amountLeftToPay) {
            category.existingAmount += amountLeftToPay;
            totalAmount -= amountLeftToPay;
            categories.remove(i);
            categoriesPanel.remove(categoriesLabels.get(i)); //the user can visually see the category being removed
            categoriesLabels.remove(i);
          }
        } else {
          i++;
        }
      } else {
        break;
      }
    }

    updateCategoryRank();

    if(categories.size() == 0) {
      cancelACategoryButton.setEnabled(false);
    }

    categoriesPanel.revalidate();
    categoriesPanel.repaint();

    return totalAmount;
  }

  public void updateWithdrawCategoryDropDownMenu() {
    categoriesComboBox.removeAllItems();

    for(int i = 0; i < categories.size(); i++) {
      categoriesComboBox.addItem(categories.get(i).title);
    }
  }

  public void addCategoriesInScrollPane() {
    categoriesPanel.removeAll();

    for(int i = 0; i < categoriesLabels.size(); i++) {
      categoriesPanel.add(categoriesLabels.get(i));
      categoriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
  }

  public void categoryDeadlineCheck() {
    for(int i = 0; i < categories.size(); i++) {
      if(categories.get(i) instanceof TimedCategory) {

        Date currentDate = new Date();
        Date categoryDate = ((TimedCategory) categories.get(i)).deadline;

        if(currentDate.compareTo(categoryDate) >= 0) {
          categoriesLabels.get(i).setForeground(Color.red);
          ((TimedCategory) categories.get(i)).deadlinePassed = true;
        }
      }
    }
    categoriesPanel.revalidate();
    categoriesPanel.repaint();
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


class MyListModel extends AbstractListModel {

  private final ArrayList<String> myArrayList = new ArrayList<String>();

  public void addElement(String obj) {
    myArrayList.add(obj);
    fireIntervalAdded(this, myArrayList.size()-1, myArrayList.size()-1);
  }

  @Override
  public Object getElementAt(int index) { return myArrayList.get(index); }

  @Override
  public int getSize() { return myArrayList.size(); }
}
