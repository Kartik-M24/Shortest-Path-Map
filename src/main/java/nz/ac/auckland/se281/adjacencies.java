package nz.ac.auckland.se281;

/**
 * Returns the tax fees of this country.
 *
 * @return the tax fees of this country
 */
public class adjacencies {
  private String currentCountry;
  private String adjacentCountry;

  /**
   * Constructs a new Adjacencies object with the given current country and adjacent country.
   *
   * @param currentCountry the current country
   * @param adjacentCountry the adjacent country
   */
  public adjacencies(String currentCountry, String adjacentCountry) {
    this.currentCountry = currentCountry;
    this.adjacentCountry = adjacentCountry;
  }
}
