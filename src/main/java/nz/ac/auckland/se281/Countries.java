package nz.ac.auckland.se281;

/** This class represents a country with its name, continent, and tax fees. */
public class Countries {
  private String name;
  private String continent;
  private double taxFees;

  /**
   * Constructs a new Countries object with the given name, continent, and tax fees.
   *
   * @param name the name of the country
   * @param continent the continent of the country
   * @param taxFees the tax fees of the country
   */
  public Countries(String name, String continent, double taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  /**
   * Checks if this country is equal to the given object.
   *
   * @param obj the object to compare this country to
   * @return true if the given object represents a country equivalent to this country, false
   *     otherwise
   */
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

  /**
   * Returns a hash code for this country.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Returns the name of this country.
   *
   * @return the name of this country
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the continent of this country.
   *
   * @return the continent of this country
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Returns the tax fees of this country.
   *
   * @return the tax fees of this country
   */
  public double getTaxFees() {
    return taxFees;
  }
}
