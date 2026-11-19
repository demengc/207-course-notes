/**
 * A <em>concrete strategy</em>: public-transit directions.
 */
public class TransitDirections implements DirectionGenerator {

  @Override
  public String getDirections(String destination) {
    return "Take transit to " + destination;
  }
}
