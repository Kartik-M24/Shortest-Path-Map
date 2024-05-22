package nz.ac.auckland.se281;

public class InvalidCountryException extends RuntimeException {

  public InvalidCountryException(String country) {
    super(MessageCli.INVALID_COUNTRY.getMessage(country));
  }
}
