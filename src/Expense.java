import java.util.Date;

public class Expense {
  private int expenseAmount;
  private String expenseType;
  private Date expenseDate;
  public Expense(String type, int amount, Date date){
    expenseAmount = amount;
    expenseDate = date;
    expenseType = type;
  }

  //GET METHODS
  public int getExpenseAmount(){
    return expenseAmount;
  }
  public String getExpenseType(){
    return expenseType;
  }
  public Date getexpenseDate(){
    return expenseDate;
  }


  //SET METHODS
  public int setExpenseAmount(int newAmount){
    expenseAmount = newAmount;
    return newAmount;
  }
  public Date setExpenseDate(Date newAmount){
    expenseDate = newAmount;
    return expenseDate;
  }
  public String setExpenseType(String newAmount){
    expenseType = newAmount;
    return expenseType;
  }
}
