package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Countries, List<Countries>> adjCountries;
  private Countries destination;
  private Countries root;

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

  public Countries getCountry(String name) {
    for (Countries country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    return null;
  }

  // I need to create a hashmap where I store the current country and the previous country it is
  // connected to and then return the route
  // Might have to add overrides for hashmap

  public List<Countries> breathFirstTraversal(Countries root, Countries destination) {
    this.destination = destination;
    this.root = root;
    List<Countries> visited = new LinkedList<>();
    Queue<Countries> queue = new LinkedList<>();
    Map<Countries, Countries> connectedCountries = new HashMap<>();
    queue.add(root);
    visited.add(root);

    while (!queue.isEmpty()
        && !visited.contains(destination)) { // Added this to check if the destination is reached
      Countries node = queue.poll();
      for (Countries neighbour : adjCountries.get(node)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          connectedCountries.put(neighbour, node);
          queue.add(neighbour);
        }
      }
    }
    return returnRoute(connectedCountries);
  }

  public List<Countries> returnRoute(Map<Countries, Countries> connectedCountries) {
    Countries destinationCountry = connectedCountries.get(this.destination);
    Countries linkedCountry;
    List<Countries> route = new LinkedList<>();
    route.add(this.destination); // Add destination first
    route.add(destinationCountry); // Add the country linked to the destination

    while (!destinationCountry.getName().equals(this.root.getName())) {
      for (Countries country : connectedCountries.keySet()) {
        if (country.getName().equals(destinationCountry.getName())) {
          linkedCountry =
              connectedCountries.get(
                  destinationCountry); // add the country linked to the destination
          route.add(linkedCountry);
          destinationCountry = linkedCountry; // set next destination to the linked country
          break; // break out of loop to rerun with new desination
        }
      }
    }

    Collections.reverse(route);
    return route;
  }
}
