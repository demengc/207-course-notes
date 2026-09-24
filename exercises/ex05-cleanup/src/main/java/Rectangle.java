/** A rectangle with dimensions that can be scaled. */
public class Rectangle {
  private double width;
  private double height;

  /**
   * Creates a rectangle with the given dimensions.
   *
   * @param width the rectangle's width
   * @param height the rectangle's height
   */
  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Returns the area of this rectangle.
   *
   * @return the width multiplied by the height
   */
  public double area() {
    return width * height;
  }

  /**
   * Scales both dimensions by the given factor.
   *
   * @param factor the amount by which to scale each dimension
   */
  public void scale(double factor) {
    width = width * factor;
    height = height * factor;
  }

  /**
   * Compares this rectangle's area with another rectangle's area.
   *
   * @param other the rectangle to compare against
   * @return true if this rectangle has a larger area than the other
   */
  public boolean isLargerThan(Rectangle other) {
    return area() > other.area();
  }
}
