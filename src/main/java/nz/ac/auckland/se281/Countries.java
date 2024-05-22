package nz.ac.auckland.se281;

public class Countries {
  private String name;
  private String continent;
  private double taxFees;

  public Countries(String name, String continent, double taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    if (!(obj instanceof Countries)) {
      return false;
    }
    Countries country = (Countries) obj;
    return name.equals(country.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public double getTaxFees() {
    return taxFees;
  }
}
