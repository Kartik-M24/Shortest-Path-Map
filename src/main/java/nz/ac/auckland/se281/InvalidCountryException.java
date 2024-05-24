package nz.ac.auckland.se281;

/**
 * Returns the route from the root country to the destination country.
 *
 * @param connectedCountries the map of connected countries
 * @return the route from the root country to the destination country
 */
public class InvalidCountryException extends Exception {

  /** Constructs a new InvalidCountryException. */
  public InvalidCountryException() {
    super();
  }
}
