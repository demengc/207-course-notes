/** Whole-string validation examples using {@link String#matches(String)}. */
public class Validators {

  /**
   * Returns whether {@code input} looks like an email address: one or more
   * "local" characters (letters, digits, or any of {@code . _ % + -}), then an
   * {@code @}, then one or more domain characters (letters, digits, {@code .},
   * or {@code -}), then a {@code .} and a top-level domain of at least two letters.
   *
   * @param input the string to test
   * @return true iff input matches the simplified email pattern
   */
  public static boolean isEmail(String input) {
    return input.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
  }

  /**
   * Returns whether {@code input} is a phone number of the form
   * {@code NNN-NNN-NNNN} (three digits, a dash, three digits, a dash, four
   * digits).
   *
   * @param input the string to test
   * @return true iff input matches the phone-number pattern
   */
  public static boolean isPhoneNumber(String input) {
    return input.matches("\\d{3}-\\d{3}-\\d{4}");
  }

  /**
   * Returns whether {@code input} has the shape of an ASCII Java identifier: it
   * starts with a letter, underscore, or dollar sign, followed by zero or more
   * letters, digits, underscores, or dollar signs.
   *
   * <p>This shape check does not reject reserved words or the lone underscore.
   *
   * @param input the string to test
   * @return true iff input matches the ASCII identifier pattern
   */
  public static boolean isJavaVariableName(String input) {
    return input.matches("[A-Za-z_$][A-Za-z0-9_$]*");
  }
}
