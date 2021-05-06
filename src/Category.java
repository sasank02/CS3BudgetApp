public class Category implements Comparable<Category> {
  String title;
  Integer weight;
  Double existingAmount;
  Double neededAmount;
  Double filledPercent;
  int specialImportance;
  boolean mandatoryFill;

  //comment
  //comment
  public Category(String cTitle, Integer cWeight, Double cExistingAmount, Double cNeededAmount, boolean cMandatoryFill  ) {
    title = cTitle;
    weight = cWeight;
    existingAmount = cExistingAmount;
    neededAmount = cNeededAmount;
    mandatoryFill = cMandatoryFill;
    filledPercent = (Double) 100.0 * existingAmount/neededAmount;
  }

  public Category() {
  }

  public void updateFilledPercent(){
    filledPercent = (Double) existingAmount/neededAmount;
  }
  public void changeTitle(String newValue){
    title = newValue;
  }
  public void changeWeight(int newValue){
    weight = newValue;
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
  public void setMandatoryFill(boolean newValue){
    mandatoryFill = newValue;
  }

  @Override
  public int compareTo(Category o) {
    return this.weight.compareTo(o.weight);
  }
}
