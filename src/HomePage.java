
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Color;

public class HomePage {

  JPanel panel;
  JButton addIncomeButton;
  JButton withdrawButton;
  JButton addCategoryButton;
  JButton cancelACategoryButton;
  JButton addFromTicketButton;
  JButton outputFileButton;

  JTextField incomeAmountField;
  JTextField otherSalaryField;
  JTextField withdrawAmountField;
  JButton askEditButton;
  JTextField sourceField;

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
    categoriesPanel.revalidate();
    categoriesPanel.repaint();

    panel.add(categoriesScrollPane);
    categoriesScrollPane.setBounds(30, 200, 240, 400);
    categoriesScrollPane.setViewportView(categoriesPanel);
    categoriesScrollPane.validate();
    categoriesScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    categoriesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    categoriesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    panel.add(categoriesComboBox);
    categoriesComboBox.setBounds(370, 505, 120,20);
    categoriesComboBox.setBorder(null);
    categoriesComboBox.addItem("Amount Left");

    recentTransactionsPanel.setLayout(new BoxLayout(recentTransactionsPanel, BoxLayout.Y_AXIS));
    recentTransactionsPane.add(recentTransactionsPanel);
    recentTransactionsPanel.revalidate();
    recentTransactionsPanel.repaint();

    panel.add(recentTransactionsPane);
    recentTransactionsPane.setBounds(605, 200, 260, 400);
    recentTransactionsPane.setViewportView(recentTransactionsPanel);
    recentTransactionsPane.validate();
    recentTransactionsPane.getVerticalScrollBar().setUnitIncrement(10);
    recentTransactionsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    recentTransactionsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    panel.add(totalAmountLeftCheckBox);
    totalAmountLeftCheckBox.setBounds(475, 455, 125, 30);

  }


  public void setListeners(){

    //TODO
//    addFromTicketButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        String path = (String) sourceField.getText();
//        System.out.println(path);
//        try {
//          Scanner sc = new Scanner(new File(path));
//          String fileType = sc.nextLine();
//
//          ArrayList<String> values = new ArrayList<String>();
//          while(sc.hasNextLine()){
//            values.add(sc.nextLine());
//          }
//
//          switch(fileType.toLowerCase()){
//            case "income":
//              int salary = (int) Integer.parseInt(values.get(0));
//              int other = (int) Integer.parseInt(values.get(1));
//
//
//              break;
//
//
//            case "withdraw":
//              int amount = (int) Integer.parseInt(values.get(0));
//              String category = (values.get(1));
//
//
//              break;
//
//
//            case "category":
//              String cName = (values.get(0));
//              int cRank = (int) Integer.parseInt(values.get(1));
//              double eExisting = (double) Double.parseDouble(values.get(2));
//              double eNeeded = (double) Double.parseDouble(values.get(3));
//              String eType = values.get(4);
//              String deadline = values.get(5);
//
//
//              break;
//
//
//            default:
//              String val = null;
//          }
//
//        } catch (FileNotFoundException fileNotFoundException) {
//          fileNotFoundException.printStackTrace();
//        }
//
//      }
//    });

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

        Object[] fields = {"Category Name:", categoryName , "Category Rank:", categoryRank, "Existing Amount:" , categoryExistingAmount, "Amount Needed:", categoryAmountNeeded, "Category Type:", regularyCategoryButton, timedCategoryButton, "Category Deadline (yyyy/MM/dd):", categoryDeadline};
        int input = JOptionPane.showConfirmDialog(null, fields, "New Category", JOptionPane.OK_CANCEL_OPTION);

        if(input == 0) { // if the user clicks ok to add a new category
          Category newCategory = null; //new category is created

          if(regularyCategoryButton.isSelected()) { // if the user created category that doesn't have a deadline
            newCategory = new Category(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()));
            String toPlace = ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" + newCategory.existingAmount + "/$" + newCategory.neededAmount;
           //TODO Recent
            JLabel newCategoryLabel = new JLabel(toPlace, SwingConstants.CENTER); //User can visually see the update in categories list
            newCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            categoriesLabels.add(newCategory.weight - 1, newCategoryLabel);


            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();

            JLabel newRecentCategoryLabel = new JLabel("<html><b>" + newCategory.title +"</b> - Category Added (" + dateFormat.format(date) +")</html>");
            newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            recentTransLabels.add(0, newRecentCategoryLabel);
            String spl = categoryDeadline.getText() + "///" + newCategory.title;
            recentTransStack.push(new Ticket("Category", 0.0, spl));




          }

          else if(timedCategoryButton.isSelected()) { // else if the user created category that does have a deadline
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date deadLine = null;
            try {
              deadLine = (Date) dateFormat.parse((String) categoryDeadline.getText()); //the deadline user entered is converte from String type to Date type variable
            } catch (ParseException parseException) {
              parseException.printStackTrace();
            }

            newCategory = new TimedCategory(categoryName.getText(), Integer.parseInt(categoryRank.getText()), Double.parseDouble(categoryExistingAmount.getText()), Double.parseDouble(categoryAmountNeeded.getText()), deadLine); // new Category is initialized as TimedCategory
            String categoryInfo = " " + ((Integer) newCategory.weight) + ") " + newCategory.title.toUpperCase() + "  $" + newCategory.existingAmount + "/$" + newCategory.neededAmount + "  Deadline: " + categoryDeadline.getText();
            String info1 = categoryInfo.substring(0, categoryInfo.indexOf("  Deadline: "));
            String info2 = categoryInfo.substring(categoryInfo.indexOf("  Deadline: "));
            //TODO Recent



            JLabel newRecentCategoryLabel = new JLabel("<html><b>" + newCategory.title +"</b> - Timed Category Added (" + dateFormat.format(new Date()) +")</html>");
            newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            recentTransLabels.add(0, newRecentCategoryLabel);
            recentTransStack.push(new Ticket("Timed Category", 0.0, dateFormat.format(new Date()) ));


            JLabel newCategoryLabel = new JLabel("<html>" + info1 + "<br/>" + info2 + "</html>");
            newCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            categoriesLabels.add(newCategory.weight - 1, newCategoryLabel);

          }

          categories.add(newCategory.weight - 1, newCategory); // new category is added to the categories (A LinkedList)


          addCategoriesInScrollPane();

          if(totalAmountLeft > 0.0) {
            totalAmountLeft = updateCategoryList(totalAmountLeft); //if totalAmountLeft is greater than 0, it will used in the categories list
          }

          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);

          updateCategoryRank(); // updates the rank of each category
          updateWithdrawCategoryDropDownMenu(); //updates the drop down menu that's in the withdraw section

          //TODO Recent
          addRecentsInScrollPane();
          recentTransactionsPanel.revalidate();
          recentTransactionsPanel.repaint();


          categoriesPanel.revalidate();
          categoriesPanel.repaint();


          //DATE
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          Date date = new Date();
          //recentTransStack.push(new Ticket("New Category", 0.0 ,  dateFormat.format(date))); //new category is pushed in the recent actions page
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
          addRecentsInScrollPane();
          updateCategoryRank();


          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          JLabel newRecentCategoryLabel = new JLabel("<html><b>" + "</b>Category Canceled (" + dateFormat.format(new Date()) +")</html>");
          newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
          newRecentCategoryLabel.setForeground(Color.RED);
          recentTransLabels.add(0, newRecentCategoryLabel);
          recentTransStack.push(new Ticket("Remove Category", 0.0,  dateFormat.format(new Date())));
          addRecentsInScrollPane();
          recentTransactionsPanel.revalidate();
          recentTransactionsPanel.repaint();

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
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          Date date = new Date();
          JLabel newRecentCategoryLabel = new JLabel("<html><b>$" + (amount+otherSalary) +"</b> - Income Added (" + dateFormat.format(date) +")</html>");
          newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
          recentTransLabels.add(0, newRecentCategoryLabel);
          recentTransStack.push(new Ticket("Income", amount+otherSalary, dateFormat.format(date) ));
          addRecentsInScrollPane();
          recentTransactionsPanel.revalidate();
          recentTransactionsPanel.repaint();

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

        if(categoriesComboBox.getSelectedIndex() == categoriesComboBox.getItemCount() - 1) {
          totalAmountLeft -= doubleTotalAmount;
          totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);
        } else {
          int categoryIndex = findCategory(sourceString);

          if (categoryIndex >= 0) {
            categories.get(categoryIndex).existingAmount -= doubleTotalAmount;
            if (totalAmountLeftCheckBox.isSelected()) {
              totalAmountLeft += doubleTotalAmount;
              totalAmountLeftLabel.setText("Amount Left: $" + totalAmountLeft);
            }


            //DATE
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();

            recentTransStack.push(new Ticket("Withdrawal", doubleTotalAmount, dateFormat.format(date)));

            JLabel newRecentCategoryLabel = new JLabel("<html><b>$" + (doubleTotalAmount) + "</b> - Withdrawal (from '" + categories.get(categoryIndex).title + "')</html>");
            newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            recentTransLabels.add(0, newRecentCategoryLabel);
            recentTransStack.push(new Ticket("Withdraw", doubleTotalAmount, dateFormat.format(date) + "///" + categories.get(categoryIndex).title));
            addRecentsInScrollPane();
            recentTransactionsPanel.revalidate();
            recentTransactionsPanel.repaint();

            categoriesLabels.get(categoryIndex).setText(" " + ((Integer) categories.get(categoryIndex).weight) + ") " + categories.get(categoryIndex).title.toUpperCase() + "  $" + categories.get(categoryIndex).existingAmount + "/$" + categories.get(categoryIndex).neededAmount);
            categoriesPanel.revalidate();
            categoriesPanel.repaint();
          } else {
            JOptionPane.showMessageDialog(null, "This category doesn't exist", "Alert", JOptionPane.WARNING_MESSAGE);
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

    outputFileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to include the entire recent actions data?", "Recent Actions", dialogButton);
        if(dialogResult == 0) {
          try {
            generateSummarySheet(true);
          } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
          }
        } else {
          try {
            generateSummarySheet(false);
          } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
          }
        }

      }
    });
  }

  public void generateSummarySheet(boolean recentIncluded) throws FileNotFoundException {
    //Read Categories
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String date = "Budget Summary Report"+dateFormat.format(new Date());
    if(recentIncluded){
      try {
        FileWriter myWriter = new FileWriter("readCategory.txt");
        new FileWriter("readCategory.txt", false).close();

        myWriter.write("Existing Category Status \n\n");
        for(Category c : categories){
          myWriter.write(c.toString() + "\n\n");
        }
        myWriter.write("Recent Transactions \n\n");

        for(Ticket c : recentTransStack){
          myWriter.write(c.toString() + "\n");
        }
        myWriter.close();

      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }


    //Recent Actions if needed
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
    frame.setSize(900, 750);
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
    categoriesLabel.setBounds(100, 100, 400, 50);
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


    addCategoryButton = new JButton("Add");
    addCategoryButton.setBounds(25, 150, 120, 30);
    addCategoryButton.setBorder(new RoundedBorder(30)); //10 is the radius
    panel.add(addCategoryButton);
    customizeButton(addCategoryButton);

    cancelACategoryButton = new JButton("Cancel");
    cancelACategoryButton.setBounds(150, 150, 120, 30);
    cancelACategoryButton.setBorder(new RoundedBorder(30)); //10 is the radius
    panel.add(cancelACategoryButton);
    customizeButton(cancelACategoryButton);



    outputFileButton = new JButton(("Generate Report"));
    outputFileButton.setBounds(330, 650, 200,30);
    outputFileButton.setBorder(new RoundedBorder(30));
    panel.add(outputFileButton);
    customizeButton(outputFileButton);

//    addFromTicketButton = new JButton(("Extract"));
//    addFromTicketButton.setBounds(365, 680, 120,30);
//    addFromTicketButton.setBorder(new RoundedBorder(30));
//    panel.add(addFromTicketButton);
//    customizeButton(addFromTicketButton);
//
//    JLabel readFromTicketLabel = new JLabel("Read From Local .txt File", SwingConstants.CENTER);
//    readFromTicketLabel.setForeground(Color.white);
//    readFromTicketLabel.setBounds(310, 610,220,30);
//    readFromTicketLabel.setFont(new Font("Heveltica", Font.BOLD, 17));
//    panel.add(readFromTicketLabel);
//
//
//    JLabel sourceLabel = new JLabel("Path: ", SwingConstants.CENTER);
//    sourceLabel.setForeground(Color.white);
//    sourceLabel.setBounds(290, 640, 80, 30);
//    sourceLabel.setFont(new Font("Heveltica", Font.PLAIN, 15));
//    panel.add(sourceLabel);
//
//
//    sourceField = new JTextField();
//    sourceField.setBounds(360, 640, 150, 20);
//    sourceField.setBorder(null);
//    sourceField.setBorder( new MatteBorder(0, 0, 2, 0, Color.white));
//    sourceField.setBackground(new Color(0, 0, 0, 0));
//    panel.add(sourceField);

//FileSystemView.getFileSystemView().getHomeDirectory()

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
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


            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            JLabel newRecentCategoryLabel = new JLabel("<html><b>" + categories.get(i).title +"</b> - Category Completed (" + dateFormat.format(new Date()) +")</html>");
            newRecentCategoryLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
            newRecentCategoryLabel.setForeground(new Color(7, 88, 11));
            recentTransLabels.add(0, newRecentCategoryLabel);
            recentTransStack.push(new Ticket("Done Category", 0.0,  dateFormat.format(new Date())));
            addRecentsInScrollPane();
            recentTransactionsPanel.revalidate();
            recentTransactionsPanel.repaint();


            categories.remove(i);
            categoriesPanel.remove(categoriesLabels.get(i)); //the user can visually see the category being removed from JPanel
            categoriesLabels.remove(i);
          }
        } else { i++; }
      } else { break; }
    }
    updateCategoryRank();

    categoriesPanel.revalidate();
    categoriesPanel.repaint();

    return totalAmount;
  }

  public void updateWithdrawCategoryDropDownMenu() {
    categoriesComboBox.removeAllItems();

    for(int i = 0; i < categories.size(); i++) {
      categoriesComboBox.addItem(categories.get(i).title);
    }

    categoriesComboBox.addItem("Amount Left");
  }

  public void addCategoriesInScrollPane() {
    categoriesPanel.removeAll();

    for(int i = 0; i < categoriesLabels.size(); i++) {
      categoriesPanel.add(categoriesLabels.get(i));
      categoriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
  }

  public void addRecentsInScrollPane() {
    recentTransactionsPanel.removeAll();

    for(int i = 0; i < recentTransLabels.size(); i++) {
      recentTransactionsPanel.add(recentTransLabels.get(i));
      recentTransactionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
