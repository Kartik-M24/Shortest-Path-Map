package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Countries, List<Countries>> adjCountries;

  public Graph() {
    this.adjCountries = new HashMap<>();
  }

  public void addCountry(Countries country) {
    adjCountries.putIfAbsent(country, new ArrayList<>());
  }

  public void addAdjacency(Countries country1, Countries country2) {
    // Ensures that the countries are in the graph
    addCountry(country1);
    addCountry(country2);
    adjCountries.get(country1).add(country2);
    adjCountries.get(country2).add(country1);
  }

  public void removeCountry(Countries country) {
    adjCountries.remove(country);
    for (Countries key : adjCountries.keySet()) {
      adjCountries.get(key).remove(country);
    }
  }

  public void removeAdjacency(Countries country1, Countries country2) {
    adjCountries.getOrDefault(country1, new ArrayList<>()).remove(country2);
    adjCountries.getOrDefault(country2, new ArrayList<>()).remove(country1);
  }

}
