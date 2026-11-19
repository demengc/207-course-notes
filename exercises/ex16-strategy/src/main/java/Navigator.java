/**
 * Strategy context that delegates to a replaceable {@link DirectionGenerator}.
 */
public class Navigator {

  private DirectionGenerator generator;

  /**
   * Creates a Navigator that uses the given strategy.
   *
   * @param generator the direction-generating strategy to start with
   */
  public Navigator(DirectionGenerator generator) {
    this.generator = generator;
  }

  /**
   * Switches to a different strategy.
   *
   * @param generator the new direction-generating strategy
   */
  public void setDirectionGenerator(DirectionGenerator generator) {
    this.generator = generator;
  }

  /**
   * Returns directions to {@code destination} by delegating to the current
   * strategy.
   *
   * @param destination where to go
   * @return the directions produced by the current strategy
   */
  public String getDirections(String destination) {
    return generator.getDirections(destination);
  }
}
