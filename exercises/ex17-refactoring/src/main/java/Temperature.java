/** An immutable temperature stored in Celsius and created through unit-specific factories. */
public class Temperature {

  private final double celsius;

  private Temperature(double celsius) {
    this.celsius = celsius;
  }

  /**
   * Returns this temperature in degrees Celsius.
   *
   * @return degrees Celsius
   */
  public double getCelsius() {
    return celsius;
  }

  /**
   * Returns this temperature in degrees Fahrenheit.
   *
   * @return degrees Fahrenheit
   */
  public double getFahrenheit() {
    return celsius * 9 / 5 + 32;
  }

  /**
   * Creates a Temperature from a value in degrees Celsius.
   *
   * @param celsius degrees Celsius
   * @return a Temperature representing that value
   */
  public static Temperature fromCelsius(double celsius) {
    return new Temperature(celsius);
  }

  /**
   * Creates a Temperature from a value in degrees Fahrenheit, converting it to
   * Celsius first.
   *
   * @param fahrenheit degrees Fahrenheit
   * @return a Temperature representing that value
   */
  public static Temperature fromFahrenheit(double fahrenheit) {
    return fromCelsius((fahrenheit - 32) * 5 / 9);
  }
}
