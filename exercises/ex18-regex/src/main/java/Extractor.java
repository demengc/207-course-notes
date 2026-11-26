import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts course codes and masks email addresses within text.
 *
 * <p>Compiled patterns are shared; each call creates its own matcher because a
 * matcher tracks the state of a particular search.
 */
public class Extractor {

  /**
   * Matches a UofT-style course code: three letters, three digits, then a
   * campus/session suffix (a letter, a digit, and a letter, such as {@code H1F}).
   * The parentheses make the three digits a <em>capturing group</em>, which
   * {@link #findCourseNumbers(String)} can pull out with {@code group(1)}.
   */
  private static final Pattern COURSE_CODE = Pattern.compile("[A-Z]{3}(\\d{3})[A-Z]\\d[A-Z]");

  /** The same simplified email pattern as {@link Validators#isEmail(String)}. */
  private static final Pattern EMAIL =
      Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");

  private static final String MASK = "***";

  /**
   * Finds every course code appearing anywhere in {@code text}, in the order they
   * appear. For example, the text {@code "Take CSC207H1F before CSC236H1S."}
   * yields {@code ["CSC207H1F", "CSC236H1S"]}.
   *
   * @param text the text to search; may contain any number of course codes
   * @return the course codes found, or an empty list if there are none (never null)
   */
  public static List<String> findCourseCodes(String text) {
    Matcher matcher = COURSE_CODE.matcher(text);
    List<String> results = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group());
    }
    return results;
  }

  /**
   * Finds just the three-digit number of every course code in {@code text}, in the
   * order they appear. For example, {@code "Take CSC207H1F before CSC236H1S."}
   * yields {@code ["207", "236"]}.
   *
   * @param text the text to search
   * @return the course numbers found, or an empty list if there are none (never null)
   */
  public static List<String> findCourseNumbers(String text) {
    Matcher matcher = COURSE_CODE.matcher(text);
    List<String> results = new ArrayList<>();
    while (matcher.find()) {
      results.add(matcher.group(1));
    }
    return results;
  }

  /**
   * Replaces each match of the simplified email pattern with {@code ***}, leaving
   * the rest of the text unchanged.
   *
   * @param text the text to mask
   * @return the text with each email address replaced by {@code ***}
   */
  public static String maskEmails(String text) {
    return EMAIL.matcher(text).replaceAll(MASK);
  }
}
