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
    List<String> adjacencies = Utils.readAdjacencies();
    Graph graph = new Graph();

    // Add countries and its  adjacencies to the graph
    for (String adjacency : adjacencies) {
      String[] adjacencyData = adjacency.split(",");
      String country1 = adjacencyData[0]; // gets the country vertex
      validCountryInput(country1); // gets the country name, continent and tax fees from the vertex
      Countries mainCountry = new Countries(countryName, continent, Double.parseDouble(taxFees));
      graph.addCountry(mainCountry);

      // Loop through the adjacent countries, adds the edge and the adjacent countries to the graph
      for (int i = 1; i < adjacencyData.length; i++) {
        String country2 = adjacencyData[i];
        validCountryInput(country2); // gets the country name, continent and tax fees
        Countries adjacentCountry =
            new Countries(countryName, continent, Double.parseDouble(taxFees));
        graph.addCountry(adjacentCountry);
        graph.addAdjacency(mainCountry, adjacentCountry);
      }
    }
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

  public void forceValidInput(String message) {
    boolean isValid = false;
    while (isValid == false) {
      System.out.println(message);
      countryInput = Utils.scanner.nextLine();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(countryInput);
      try {
        isValid = checkInputException(countryInput);
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String message = MessageCli.INSERT_COUNTRY.getMessage();
    forceValidInput(message);
    MessageCli.COUNTRY_INFO.printMessage(countryName, continent, taxFees);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String countryFrom = MessageCli.INSERT_COUNTRY.getMessage();
    forceValidInput(countryFrom);
    String countryTo = MessageCli.INSERT_DESTINATION.getMessage();
    forceValidInput(countryTo);
  }
}
