public class Category {
  String title;
  Double weight;
  Double existingAmount;
  Double neededAmount;
  Double filledPercent;
  int specialImportance;
  boolean mandatoryFill;
  //comment
  public Category(String cTitle, Double cWeight, Double cExistingAmount, Double cNeededAmount, int cSpecialImportance, boolean cMandatoryFill  ) {
    title = cTitle;
    weight = cWeight;
    existingAmount = cExistingAmount;
    neededAmount = cNeededAmount;
    specialImportance = cSpecialImportance;
    mandatoryFill = cMandatoryFill;
    filledPercent = (Double) existingAmount/neededAmount;
  }

  public void updateFilledPercent(){
    filledPercent = (Double) existingAmount/neededAmount;
  }
  public void changeTitle(String newValue){
    title = newValue;
  }
  public void changeWeight(Double newValue){
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

}
