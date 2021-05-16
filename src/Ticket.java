import javax.swing.*;
import java.util.Date;

public class Ticket {
  private Double expenseAmount;
  private String expenseType;
  private String expenseDate;
  public Ticket(String type, Double amount, String date){
    expenseAmount = amount;
    expenseDate = date;
    expenseType = type;
  }
  //GET METHODS
  public Double getExpenseAmount(){
    return expenseAmount;
  }
  public String getExpenseType(){
    return expenseType;
  }
  public String getExpenseDate(){
    return expenseDate;
  }

  //SET METHODS
  public Double setExpenseAmount(Double newAmount){
    expenseAmount = newAmount;
    return newAmount;
  }

  public String setExpenseDate(String newAmount){
    expenseDate = newAmount;
    return expenseDate;
  }

  public String setExpenseType(String newAmount){
    expenseType = newAmount;
    return expenseType;
  }

  public int compareTo(Ticket e, String comparator){
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


  public String toString(){
    switch(expenseType){
      case "Income":
        return "$" + (expenseAmount) + " - Income Added (" + expenseDate +")";
      case "Withdraw":
       String[] spl = expenseDate.split("///");
       return "$" + (expenseAmount) +" - Withdrawal (from '" + spl[1]  +"')";
      case "Category":
        String[] spsl = expenseDate.split("///");
        return spsl[1] +" - Category Added (" + spsl[0] +")";
      case "Remove Category":
        return "Category Canceled (" + expenseDate + ")";

      default:
        return "";
    }
  }
}