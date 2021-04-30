import java.util.Date;

public class Expense {
  private int expenseAmount;
  private String expenseType;
  private Date expenseDate;
  public Expense(String type, int amount, Date date){
    expenseAmount = amount;
    expenseDate = date;
    expenseType = type;
    //new comment for commit
  }

  //GET METHODS
  public int getExpenseAmount(){
    return expenseAmount;
  }
  public String getExpenseType(){
    return expenseType;
  }
  public Date getExpenseDate(){
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

  public int compareTo(Expense e, String comparator){
    if(comparator == "Amount"){
      if(e.getExpenseAmount() > expenseAmount) return -1; //e is larger
      if(e.getExpenseAmount() < expenseAmount) return 1;
      return 0;
    }
    else if(comparator == "Date"){
      return expenseDate.compareTo(e.getExpenseDate());

    }
    else{
      if(comparator == "Type") return expenseType.compareTo(e.getExpenseType());
    }
    return 333;
  }

}
