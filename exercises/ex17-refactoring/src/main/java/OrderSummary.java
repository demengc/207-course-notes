import java.util.Locale;

/**
 * Calculates and formats an order summary.
 */
public class OrderSummary {

  private static final double DISCOUNT_THRESHOLD = 200.0;
  private static final double DISCOUNT_RATE = 0.10;
  private static final double TAX_RATE = 0.13;
  private static final double PREMIUM_PRICE_THRESHOLD = 100.0;

  /**
   * Builds a human-readable, multi-line summary of an order.
   *
   * <p>An order gets a 10% discount when its subtotal is more than $200, and tax
   * is then charged on the discounted amount. An item costing $100 or more counts
   * as a "premium" item.
   *
   * @param customer the customer's name
   * @param itemNames the name of each item, in order
   * @param itemPrices the price of each item, in the same order as {@code itemNames}
   * @return the formatted summary, with lines separated by {@code \n}
   */
  public static String summarize(String customer, String[] itemNames, double[] itemPrices) {
    double subtotal = calculateSubtotal(itemPrices);
    double discount = subtotal > DISCOUNT_THRESHOLD ? subtotal * DISCOUNT_RATE : 0.0;
    double taxable = subtotal - discount;
    double tax = taxable * TAX_RATE;
    double total = taxable + tax;

    int premiumCount = countPremiumItems(itemPrices);
    return formatReport(
        customer, itemNames, itemPrices, premiumCount, subtotal, discount, tax, total);
  }

  private static double calculateSubtotal(double[] itemPrices) {
    double subtotal = 0.0;
    for (double price : itemPrices) {
      subtotal += price;
    }
    return subtotal;
  }

  private static int countPremiumItems(double[] itemPrices) {
    int premiumCount = 0;
    for (double price : itemPrices) {
      if (price >= PREMIUM_PRICE_THRESHOLD) {
        premiumCount++;
      }
    }
    return premiumCount;
  }

  private static String formatReport(
      String customer,
      String[] itemNames,
      double[] itemPrices,
      int premiumCount,
      double subtotal,
      double discount,
      double tax,
      double total) {
    StringBuilder report = new StringBuilder();
    report.append("Order summary for ").append(customer).append("\n");
    report.append("----------------------\n");
    for (int i = 0; i < itemNames.length; i++) {
      report.append(formatItemLine(itemNames[i], itemPrices[i]));
    }
    report.append(String.format(Locale.US, "Items: %d\n", itemNames.length));
    report.append(String.format(Locale.US, "Premium items: %d\n", premiumCount));
    report.append(String.format(Locale.US, "Subtotal: $%.2f\n", subtotal));
    report.append(String.format(Locale.US, "Discount: $%.2f\n", discount));
    report.append(String.format(Locale.US, "Tax: $%.2f\n", tax));
    report.append(String.format(Locale.US, "Total: $%.2f", total));
    return report.toString();
  }

  private static String formatItemLine(String itemName, double itemPrice) {
    return String.format(Locale.US, "%s: $%.2f\n", itemName, itemPrice);
  }
}
