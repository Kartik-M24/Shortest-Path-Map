package nz.ac.auckland.se281;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is the main entry point for the MapEngine application. It is responsible for loading
 * and managing the map data.
 */
public class MapEngine {

  private String countryInput;
  private String countryName;
  private String continent;
  private String taxFees;
  private Graph graph;

  /** The constructor for the MapEngine class. It initializes the class and loads the map data. */
  public MapEngine() {
    loadMap(); // keep this method invocation
  }

  /**
   * This method is invoked one time only when constructing the MapEngine class. It is responsible
   * for loading the adjacency list and initializing the graph.
   */
  private void loadMap() {
    List<String> adjacencies = Utils.readAdjacencies();
    graph = new Graph();

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

  /**
   * This method checks if the country input is valid.
   *
   * @param countryInput The country input to be checked.
   * @return true if the country input is valid, false otherwise.
   */
  public boolean validCountryInput(String countryInput) {
    List<String> countries = Utils.readCountries(); // reads the countries from the csv file
    for (String country : countries) {
      String[] countryData = country.split(",");
      if (countryData[0].equals(countryInput)) { // checks if the country input is valid
        // If it is valid, gets the country name, continent and tax fees for that country to be used
        // in other methods
        countryName = countryData[0];
        continent = countryData[1];
        taxFees = countryData[2];
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks if the country input is valid and throws an exception if it is not.
   *
   * @param countryInput The country input to be checked.
   * @return true if the country input is valid.
   * @throws InvalidCountryException if the country input is not valid.
   */
  public boolean checkInputException(String countryInput) throws InvalidCountryException {
    if (validCountryInput(countryInput) == false) {
      throw new InvalidCountryException();
    }
    return true;
  }

  /** This method loops until User has entered a valid country input. */
  public void forceValidInput() {
    boolean isValid = false;
    while (isValid == false) { // loops until the user enters a valid country
      countryInput = Utils.scanner.nextLine(); // gets the country input from the user
      countryInput =
          Utils.capitalizeFirstLetterOfEachWord(
              countryInput); // capitalizes the starting letter of each word
      try {
        isValid = checkInputException(countryInput);
      } catch (InvalidCountryException e) { // catches the exception if the country input is invalid
        MessageCli.INVALID_COUNTRY.printMessage(countryInput);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // Get the country information and print it
    MessageCli.INSERT_COUNTRY.printMessage();
    forceValidInput();
    MessageCli.COUNTRY_INFO.printMessage(countryName, continent, taxFees);
  }

  /** This method is invoked when the user run the command route. */
  public void showRoute() {
    // Get the source and destination countries
    MessageCli.INSERT_SOURCE.printMessage();
    forceValidInput();
    Countries sourceCountry = graph.getCountry(countryInput);
    MessageCli.INSERT_DESTINATION.printMessage();
    forceValidInput();
    Countries destinationCountry = graph.getCountry(countryInput);

    // Get the appropriate values and print the route information
    if (sourceCountry.getName().equals(destinationCountry.getName())) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    } else {
      List<Countries> routeReferences =
          graph.breathFirstTraversalRouteFinder(sourceCountry, destinationCountry);
      CopyOnWriteArrayList<String> continentsRoute = new CopyOnWriteArrayList<>();
      int totalTax = 0;
      StringBuilder routeList = new StringBuilder();

      for (int i = 0; i < routeReferences.size(); i++) {
        routeList.append(routeReferences.get(i).getName());
        continentsRoute.addIfAbsent(routeReferences.get(i).getContinent());
        if (i >= 1) {
          totalTax += routeReferences.get(i).getTaxFees();
        }
        if (i != routeReferences.size() - 1) {
          routeList.append(", ");
        }
      }
      MessageCli.ROUTE_INFO.printMessage("[" + routeList.toString() + "]");
      MessageCli.CONTINENT_INFO.printMessage(continentsRoute.toString());
      MessageCli.TAX_INFO.printMessage(String.valueOf(totalTax));
    }
  }
}
