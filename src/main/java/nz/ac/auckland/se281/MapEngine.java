package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private String countryInput;
  private String countryName;
  private String continent;
  private String taxFees;

  public MapEngine() {
    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constructing the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // Create the graph using a countries class and an adjacencies class
    Graph graph = new Graph();
    // Add the countries to the graph
    for (String country : countries) {
      String[] countryData = country.split(",");
      Countries newCountry =
          new Countries(countryData[0], countryData[1], Double.parseDouble(countryData[2]));
      graph.addCountry(newCountry);
    }
    // Add the adjacencies to the graph
  }

  public boolean validCountryInput(String countryInput) {
    List<String> countries = Utils.readCountries();
    for (String country : countries) {
      String[] countryData = country.split(",");
      if (countryData[0].equals(countryInput)) {
        countryName = countryData[0];
        continent = countryData[1];
        taxFees = countryData[2];
        return true;
      }
    }
    return false;
  }

  public boolean checkInputException(String countryInput) throws InvalidCountryException {
    if (validCountryInput(countryInput) == false) {
      throw new InvalidCountryException();
    }
    return true;
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    boolean isValid = false;
    while (isValid == false) {
      MessageCli.INSERT_COUNTRY.printMessage();
      countryInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(countryInput);
      try {
        isValid = checkInputException(countryInput);
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }
    MessageCli.COUNTRY_INFO.printMessage(countryName, continent, taxFees);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
