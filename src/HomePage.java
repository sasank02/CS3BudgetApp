
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.TreeMap;

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
  JList categoriesList;
  MyListModel categoryListModel;
  Category leftover;
  TreeMap<Integer, Category> categoriesMap = new TreeMap<Integer, Category>();
  //recommit
  public HomePage() {
    setGraphics();

    categoryListModel = new MyListModel();
    categoriesList=new JList(categoryListModel);
    categoriesList.setBounds(30, 200, 240, 400);
    categoriesList.setFixedCellHeight(50);
    categoriesList.setFont(new Font("Heveltica",Font.BOLD,14));

    leftover = new Category("Leftover", 10000, 0.0, Double.MAX_VALUE, false);

    categoriesMap.put(1000, leftover);

    String toPlace = "" + (leftover.title.toUpperCase() + "  $" +  leftover.existingAmount);
    categoryListModel.addElement(toPlace);

    panel.add(categoriesList);
  }


  public void setListeners(){
    addCategoryButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField categoryName = new JTextField();
        JTextField categoryRank = new JTextField();
        JTextField categoryExistingAmount = new JTextField();
        JTextField categoryAmountNeeded = new JTextField();
        JTextField categoryMandatory = new JTextField();


        Object[] fields = {"Category Name:", categoryName , "Category Rank:", categoryRank, "Existing Amount:" , categoryExistingAmount, "Amount Needed:", categoryAmountNeeded , "Mandatory Category?", categoryMandatory};
        int input = JOptionPane.showConfirmDialog(null, fields, "New Category", JOptionPane.OK_CANCEL_OPTION);

        boolean mandatoryToFill = false;
        if(categoryMandatory.getText().toLowerCase().equals("yes")) mandatoryToFill = true;
        Category newCategory =  new Category(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()), mandatoryToFill);
        //TODO: VALIDATE FIELDS

        String toPlace = "" + ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" +  newCategory.existingAmount +"/$" +  newCategory.neededAmount;
        categoryListModel.addElement(toPlace);
        categoriesMap.put(Integer.parseInt(categoryRank.getText()), newCategory);

//        System.out.print("INPUT: \n" + newCategory.title + "\n" + newCategory.weight + "\n" + newCategory.existingAmount + "\n" + newCategory.filledPercent + "\n" + newCategory.neededAmount + "\n" + newCategory.mandatoryFill);
        //categoriesList.updateUI();
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
        Double totalAmount = Double.parseDouble(amountString) + Double.parseDouble(otherSalaryString);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to add $" + totalAmount + " as income?", "Confirm", dialogButton);
        if(dialogResult == 0) {
          //YES
          //spread evenly\
          spreadEvenly(totalAmount);
        } else {
          //NO
          //do nothing
        }
      }
    });

    withdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
//        //SECURE DATA FROM TEXT FIELDS
//        withdrawAmountField.setEditable(false);
//        withdrawSourceField.setEditable(false);
//
//        String amountString = (String) withdrawAmountField.getText();
//        String sourceString = (String) withdrawSourceField.getText();
//
//        Integer totalAmount = Integer.parseInt(amountString);
//        Double doubleTotalAmount = Double.parseDouble(amountString);
//
//        //Remove MONEY From CATEGORY SPREAD EVENLY
//        Category category = findCategory(sourceString);
//        category.deltaExistingAmount((doubleTotalAmount * -1));
//
////        if(categoriesMap.containsKey(sourceString)) {
////          categoriesMap.remove(sourceString, (Integer) totalAmount);
////        }

        withdrawAmountField.setEditable(false);
        withdrawSourceField.setEditable(false);

        Double amountString = Double.parseDouble(withdrawAmountField.getText());
        String source = (String) withdrawSourceField.getText();

        if(source.toLowerCase().equals("none")){
          int dialogButton = JOptionPane.YES_NO_OPTION;
          int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to withdraw $" + amountString + " from '" + source + "'?", "Confirm", dialogButton);
          if(dialogResult == 0) {
            //YES
            //spread evenly\
            removeEvenly(amountString);
          } else {
            //NO
            //do nothing
          }
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

  public void spreadEvenly(Double amount){
      System.out.print("\nbefore\n");
    for(Map.Entry<Integer, Category> category : categoriesMap.entrySet()){
      System.out.println("--> " + category.getValue().existingAmount + " //// needed: " + (category.getValue().neededAmount));
    }
      //get all categories list
      for(Map.Entry<Integer, Category> category : categoriesMap.entrySet()){

        Double needed = category.getValue().neededAmount - category.getValue().existingAmount;
        if(needed != 0 && amount != 0) {
          if (needed < amount) {
            System.out.println("adding " + amount);
            category.getValue().existingAmount += needed;
            amount -= needed;

          } else if (needed >= amount) {
            category.getValue().existingAmount += amount;
            amount = 0.0;
          }
        }

      }

      leftover.existingAmount += amount;
      System.out.print("\n\nLeftover: " + leftover.existingAmount);
      System.out.print("\n\nafter\n\n");
      for(Map.Entry<Integer, Category> category : categoriesMap.entrySet()){
        System.out.println("--> " + category.getValue().existingAmount + " //// needed: " + (category.getValue().neededAmount));
      }
      categoriesList.updateUI();
  }




  public void removeEvenly(Double amount){
    System.out.print("\nbefore\n");
    for(Map.Entry<Integer, Category> category : categoriesMap.entrySet()){
      System.out.println("--> " + category.getValue().existingAmount + " //// needed: " + (category.getValue().neededAmount));
    }

    for(int i = categoriesMap.size()-1; i >0; i--){
      System.out.println("LOOKING FOR: " + i);
      Category category = categoriesMap.get(i);
      Double value = category.existingAmount;
      if(amount != 0 && value !=0) {
        if (amount < value) {
          System.out.println("removing " + amount);
          category.existingAmount -= amount;
          amount = 0.0 ;

        } else if (amount >= value) {

          category.existingAmount  = 0.0;
          amount = amount - value;
        }
      }
      if(value < 0){
        JOptionPane.showMessageDialog(null, "Cannot withdraw. Insufficient funds");
      }

    }

    leftover.existingAmount -= amount;

    System.out.print("\n\nafter\n\n");
    for(Map.Entry<Integer, Category> category : categoriesMap.entrySet()){
      System.out.println("--> " + category.getValue().existingAmount + " //// needed: " + (category.getValue().neededAmount));
    }
    categoriesList.updateUI();
  }


  public Category findCategory(String src){
    return null;
  }

  public void setGraphics() {
    JFrame frame = new JFrame("Budget");
    frame.setSize(600, 700);
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
    withdrawAmountField.setBounds(370, 455, 150, 20);
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

