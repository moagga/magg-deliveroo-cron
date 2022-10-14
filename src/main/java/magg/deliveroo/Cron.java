package magg.deliveroo;

import java.util.Collections;
import java.util.List;

/**
 * Class to represent a Cron expression, using standard cron syntax <code>* * * * *</code>
 * <p/>Allowed characters for describing cron are:
 * <li/>*	any value
 * <li/>,	value list separator
 * <li/>-	range of values
 * <li/>/	step values
 *
 */
public final class Cron {

  /**
   * Parses a string expression into Cron object.
   *
   * @param expression
   * @return
   */
  public static Cron parse(String expression) {
    String[] parts = expression.split("\\s");
    if (parts.length != 6) {
      throw new IllegalArgumentException("Invalid expression");
    }

    Cron cron = new Cron();

    cron.minutes = new TimeUnit(0,59).parseIntoValues(parts[0]);
    cron.hours = new TimeUnit(0,23).parseIntoValues(parts[1]);
    cron.daysOfMonth = new TimeUnit(1,31).parseIntoValues(parts[2]);
    cron.months = new TimeUnit(1,12).parseIntoValues(parts[3]);
    cron.daysOfWeek = new TimeUnit(1,7).parseIntoValues(parts[4]);
    cron.command = parts[5];

    return cron;
  }

  private List<Integer> minutes;
  private List<Integer> hours;
  private List<Integer> daysOfMonth;
  private List<Integer> months;
  private List<Integer> daysOfWeek;
  private String command;

  private Cron() {
  }

  public List<Integer> minutes() {
    return Collections.unmodifiableList(minutes);
  }

  public List<Integer> hours() {
    return Collections.unmodifiableList(hours);
  }

  public List<Integer> daysOfMonth() {
    return Collections.unmodifiableList(daysOfMonth);
  }

  public List<Integer> months() {
    return Collections.unmodifiableList(months);
  }

  public List<Integer> daysOfWeek() {
    return Collections.unmodifiableList(daysOfWeek);
  }

  public String command() {
    return command;
  }
}

