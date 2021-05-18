
import java.util.Date;

public class TimedCategory extends Category {

    Date deadline;
    boolean deadlinePassed;

    public TimedCategory(String cTitle, Integer cWeight, Double cExistingAmount, Double cNeededAmount, Date date) {
        super(cTitle, cWeight, cExistingAmount, cNeededAmount);
        deadline = date;

        if(new Date().compareTo(deadline) < 0) {
            deadlinePassed = false;
        } else {
            deadlinePassed = true;
        }
    }


  public String toString(){ //returns string
    filledPercent = (Double) 100.0 * existingAmount/neededAmount;
    return ("Rank: " + weight + "\n"
      + "Title: " + title + "\n"
      + "Existing Amount: " + existingAmount + "\n"
      + "Required Amount: " + neededAmount + "\n"
      + filledPercent + "% Filled" +"\n"
    + "Deadline: " + deadline.toString());
  }
}
