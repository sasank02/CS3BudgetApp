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
}
