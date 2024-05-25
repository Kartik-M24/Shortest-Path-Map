package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class represents a graph of countries. It is used to find the shortest path between two
 * countries.
 */
public class Graph {
  private Map<Countries, List<Countries>> adjCountries;
  private Countries destination;
  private Countries root;

  /** Constructs a new Graph object. */
  public Graph() {
    this.adjCountries = new HashMap<>();
  }

  /**
   * Adds a country to the graph.
   *
   * @param country the country to add
   */
  public void addCountry(Countries country) {
    adjCountries.putIfAbsent(country, new ArrayList<>());
  }

  /**
   * Adds an adjacency between two countries to the graph.
   *
   * @param country1 the first country
   * @param country2 the second country
   */
  public void addAdjacency(Countries country1, Countries country2) {
    // Ensures that the countries are in the graph
    addCountry(country1);
    addCountry(country2);
    adjCountries.get(country1).add(country2);
  }

  /**
   * Removes a country from the graph.
   *
   * @param country the country to remove
   */
  public void removeCountry(Countries country) {
    adjCountries.remove(country);
    for (Countries key : adjCountries.keySet()) {
      adjCountries.get(key).remove(country);
    }
  }

  /**
   * Removes an adjacency between two countries from the graph.
   *
   * @param country1 the first country
   * @param country2 the second country
   */
  public void removeAdjacency(Countries country1, Countries country2) {
    adjCountries.getOrDefault(country1, new ArrayList<>()).remove(country2);
    adjCountries.getOrDefault(country2, new ArrayList<>()).remove(country1);
  }

  /**
   * Returns the country with the given name.
   *
   * @param name the name of the country to return
   * @return the country with the given name, or null if no such country exists
   */
  public Countries getCountry(String name) {
    for (Countries country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    return null;
  }

  /**
   * Returns the shortest route from the root country to the destination country.
   *
   * @param root the root country
   * @param destination the destination country
   * @return the shortest route from the root country to the destination country
   */
  public List<Countries> breathFirstTraversalRouteFinder(Countries root, Countries destination) {
    this.destination = destination;
    this.root = root;
    List<Countries> visited = new LinkedList<>();
    Queue<Countries> queue = new LinkedList<>();
    Map<Countries, Countries> connectedCountries = new LinkedHashMap<>();
    queue.add(root);
    visited.add(root);

    while (!queue.isEmpty()
        && !visited.contains(destination)) { // Checks if the destination is reached
      Countries node = queue.poll();
      for (Countries neighbour : adjCountries.get(node)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          connectedCountries.put(
              neighbour,
              node); // Add the country and the country it is linked to allow for backtracking to
          // root from destination
          queue.add(neighbour);
        }
      }
    }
    return returnRoute(connectedCountries);
  }

  /**
   * Returns the route from the root country to the destination country.
   *
   * @param connectedCountries the map of connected countries
   * @return the route from the root country to the destination country
   */
  public List<Countries> returnRoute(Map<Countries, Countries> connectedCountries) {
    // The following two form the chains between the two country vertex along the path to the
    // desination
    Countries destinationCountry = connectedCountries.get(this.destination);
    Countries linkedCountry;

    List<Countries> route = new LinkedList<>();
    route.add(this.destination); // Add destination first
    route.add(destinationCountry); // Add the country linked to the destination

    // Start from the destination and work back to the root
    while (!destinationCountry.getName().equals(this.root.getName())) {
      for (Countries country : connectedCountries.keySet()) {
        if (country.getName().equals(destinationCountry.getName())) {
          linkedCountry =
              connectedCountries.get(
                  destinationCountry); // gets the country linked to the destination
          route.add(linkedCountry); // add the linked country to the route
          destinationCountry = linkedCountry; // set next destination to the linked country
          break; // then repeat the process until the root is reached
        }
      }
    }

    Collections.reverse(route); // As we worked backwards we must reverse the list
    return route;
  }
}
