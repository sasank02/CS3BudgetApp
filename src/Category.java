public class Category implements Comparable<Category> {
  String title;
  Integer weight;
  Double existingAmount;
  Double neededAmount;
  Double filledPercent;
  //comment
  //comment comment
  public Category(String cTitle, Integer cWeight, Double cExistingAmount, Double cNeededAmount) {
    title = cTitle;
    weight = cWeight;
    existingAmount = cExistingAmount;
    neededAmount = cNeededAmount;
    filledPercent = (Double) 100.0 * existingAmount/neededAmount;
  }

  public void updateFilledPercent(){
    filledPercent = (Double) existingAmount/neededAmount;
  }
  public void deltaExistingAmount(Double newValue){
    existingAmount += newValue;
    updateFilledPercent();
  }
  public void changeNeededAmount(Double newValue){
    neededAmount = newValue;
    updateFilledPercent();
  }

  public String toString(){
    filledPercent = (Double) 100.0 * existingAmount/neededAmount;
   return ("Rank: " + weight + "\n"
     + "Title: " + title + "\n"
     + "Existing Amount: " + existingAmount + "\n"
     + "Required Amount: " + neededAmount + "\n"
     + filledPercent + "% Filled");
  }

  @Override
  public int compareTo(Category o) {
    return this.weight.compareTo(o.weight);
  }
}