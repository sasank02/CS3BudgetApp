public class Category implements Comparable<Category> {
  String title;
  Integer weight;
  Double existingAmount;
  Double neededAmount;
  Double filledPercent;
  int specialImportance;
  //comment
  //comment
  public Category(String cTitle, Integer cWeight, Double cExistingAmount, Double cNeededAmount) {
    title = cTitle;
    weight = cWeight;
    existingAmount = cExistingAmount;
    neededAmount = cNeededAmount;
    filledPercent = (Double) 100.0 * existingAmount/neededAmount;
  }

  public Category() {
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
  public void setSpecialImportance(int newValue){
     specialImportance = newValue;
  }

  @Override
  public int compareTo(Category o) {
    return this.weight.compareTo(o.weight);
  }
}
