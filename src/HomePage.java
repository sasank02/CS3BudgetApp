
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class HomePage {

  JPanel panel;
  JButton addIncomeButton;
  JButton withdrawButton;
  JButton addCategoryButton;
  JTextField incomeAmountField;
  JTextField otherSalaryField;
  JTextField withdrawAmountField;
  JTextField withdrawSourceField;
  JButton askEditButton;
  //JList categoriesList;
  //MyListModel categoryListModel;

  ArrayList<Category> categories = new ArrayList<Category>();
  Stack<Category> categoryStack = new Stack<Category>();
  ArrayList<JLabel> categoriesLabels = new ArrayList<JLabel>();

  Double totalAmountLeft = 0.0;
  JLabel totalAmountLeftLabel = new JLabel("Amount Left: $" + totalAmountLeft);
  JCheckBox totalAmountLeftCheckBox = new JCheckBox("+ Amount Left");

  JScrollPane categoriesScrollPane = new JScrollPane();
  JPanel categoriesPanel = new JPanel();

  public HomePage() {
    setGraphics();

    /*categoryListModel = new MyListModel();
    categoriesList=new JList(categoryListModel);
    categoriesList.setBounds(30, 200, 240, 400);
    categoriesList.setFixedCellHeight(50);
    categoriesList.setFont(new Font("Heveltica",Font.BOLD,14));
      */
    //panel.add(categoriesList);

    panel.add(totalAmountLeftLabel);
    totalAmountLeftLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
    totalAmountLeftLabel.setBounds(30, 600 + 10, 200, 40);

    categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
    categoriesScrollPane.add(categoriesPanel);
    categoriesPanel.revalidate();
    categoriesPanel.repaint();
    categoriesPanel.setPreferredSize(new Dimension(240, 400));
    panel.add(categoriesScrollPane);
    categoriesScrollPane.setBounds(30, 200, 240, 400);
    categoriesScrollPane.setViewportView(categoriesPanel);
    categoriesScrollPane.validate();
    categoriesScrollPane.getVerticalScrollBar().setUnitIncrement(10);

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


        Object[] fields = {"Category Name:", categoryName , "Category Rank:", categoryRank, "Existing Amount:" , categoryExistingAmount, "Amount Needed:", categoryAmountNeeded};
        int input = JOptionPane.showConfirmDialog(null, fields, "New Category", JOptionPane.OK_CANCEL_OPTION);

        if(input == 0) {
          Category newCategory = new Category(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()));
          //TODO: VALIDATE FIELDS

          String toPlace = " " + ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" + newCategory.existingAmount + "/$" + newCategory.neededAmount;
          //categoryListModel.addElement(toPlace);
          categoriesPanel.removeAll();

          categories.add(newCategory.weight - 1, newCategory);

          JLabel newCategoryLabel = new JLabel(toPlace, SwingConstants.CENTER); //User can see the update in categories list
          newCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
          categoriesLabels.add(newCategory.weight - 1, newCategoryLabel);

          for(int i = 0; i < categoriesLabels.size(); i++) {
            categoriesPanel.add(categoriesLabels.get(i));
          }

          if(totalAmountLeft > 0.0) {
            totalAmountLeft = updateCategoryList(totalAmountLeft);
          }

          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);

          updateCategoryRank(); // updates the rank

          categoriesPanel.revalidate();
          categoriesPanel.repaint();

          System.out.println();

          for(int k = 0; k < categories.size(); k++) { // prints the list
            Category category = categories.get(k);
            System.out.println("INPUT: " + "Category: " + category.title + ",  " + "Rank: " + category.weight + ",  " + "Filled Percent: " + category.existingAmount + "/" + category.neededAmount);
          }
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

        if(amount > 0.0 || otherSalary > 0.0) {
          Double totalAmount = amount + otherSalary + totalAmountLeft;
          totalAmountLeft = 0.0;

          //ADD MONEY TO CATEGORY SPREAD EVENLY

          totalAmountLeft += updateCategoryList(totalAmount);
          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);

          updateCategoryRank();

          System.out.println();

          for (int k = 0; k < categories.size(); k++) {
            Category newCategory = categories.get(k);
            System.out.println("INPUT: " + "Category: " + newCategory.title + ",  " + "Rank: " + newCategory.weight + ",  " + "Filled Percent: " + newCategory.existingAmount + "/" + newCategory.neededAmount);
          }
        }
      }
    });

    withdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        withdrawAmountField.setEditable(false);
        withdrawSourceField.setEditable(false);

        String amountString = (String) withdrawAmountField.getText();
        String sourceString = (String) withdrawSourceField.getText();

        Double doubleTotalAmount = Double.parseDouble(amountString);

        //Remove MONEY From CATEGORY SPREAD EVENLY

        int categoryIndex = findCategory(sourceString);

        if(categoryIndex >= 0) {
          categories.get(categoryIndex).existingAmount -= doubleTotalAmount;
          if(totalAmountLeftCheckBox.isSelected()) {
            totalAmountLeft += doubleTotalAmount;
            totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);
          }
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
    frame.setSize(625, 700);
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
    titleLabel.setBounds(180, 40, 400, 50);
    titleLabel.setFont(new Font("Heveltica", Font.BOLD, 25));

    JLabel categoriesLabel = new JLabel("Categories");
    categoriesLabel.setForeground(Color.white);
    categoriesLabel.setBounds(60, 100, 400, 50);
    categoriesLabel.setFont(new Font("Heveltica", Font.BOLD, 18));

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

    withdrawSourceField = new JTextField();
    withdrawSourceField.setBounds(370, 505, 150, 20);
    withdrawSourceField.setBorder(null);
    withdrawSourceField.setBorder( new MatteBorder(0, 0, 2, 0, Color.white));
    withdrawSourceField.setBackground(new Color(0, 0, 0, 0));

    withdrawButton = new JButton("Withdraw");
    withdrawButton.setBounds(350, 555, 150, 30);
    customizeButton(withdrawButton);
    withdrawSourceField.setEditable(false);
    withdrawAmountField.setEditable(false);

    JButton askWithdrawButton = new JButton("Edit");
    askWithdrawButton.setBounds(370, 415, 115, 22);
    askWithdrawButton.setBorder(new RoundedBorder(22)); //10 is the radius
    askWithdrawButton.setForeground(Color.white);
    panel.add(askWithdrawButton);

    askWithdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //SECURE DATA FROM TEXT FIELDS
        if(withdrawSourceField.isEditable()){
          withdrawSourceField.setEditable(false);
          withdrawAmountField.setEditable(false);
        }
        else{
          withdrawSourceField.setEditable(true);
          withdrawAmountField.setEditable(true);
        }

      }
    });


    addCategoryButton = new JButton("Add Category");
    addCategoryButton.setBounds(40, 150, 150, 30);
    addCategoryButton.setBorder(new RoundedBorder(30)); //10 is the radius
    addCategoryButton.setForeground(Color.white);
    panel.add(addCategoryButton);
//comment
    //comment

    panel.add(withdrawAmountLabel);
    panel.add(withdrawAmountField);
    panel.add(withdrawSourceLabel);
    panel.add(withdrawSourceField);
    panel.add(withdrawButton);

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

  }

  public void customizeButton(JButton button) {
    button.setBorder(new RoundedBorder(30)); //10 is the radius
  }

  public void updateCategoryRank() {
    //assigns new rank for each category if categories are removed
    for (int i = 0; i < categories.size(); i++) {
      categories.get(i).weight = i + 1;
      categoriesLabels.get(i).setText(" " + ((Integer) categories.get(i).weight) + ") " + categories.get(i).title.toUpperCase() + "  $" + categories.get(i).existingAmount + "/$" + categories.get(i).neededAmount);
    }
  }

  public Double updateCategoryList(Double totalAmount) {
    int i = 0;
    while(i < categories.size()) {
      if(totalAmount > 0.0) {
        Category category = categories.get(i);
        Double amountLeftToPay = category.neededAmount - category.existingAmount;
        if(totalAmount < amountLeftToPay) {
          category.existingAmount += totalAmount;
          totalAmount = 0.0;
        } else if(totalAmount >= amountLeftToPay) {
          category.existingAmount += amountLeftToPay;
          totalAmount -= amountLeftToPay;
          categoryStack.push(category); //adds the category to the stack
          categories.remove(i);
          categoriesPanel.remove(categoriesLabels.get(i)); //removes the category from the categories list (ScrollPane)
          categoriesLabels.remove(i);
        }
      } else {
        break;
      }
    }

    updateCategoryRank();

    categoriesPanel.revalidate();
    categoriesPanel.repaint();

    return totalAmount;
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

