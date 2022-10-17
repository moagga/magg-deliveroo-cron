package magg.deliveroo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represent a time unit (minute, hour, month) in cron expression.
 * It takes a low & high value for the unit (ex: 0 & 59 for minutes)
 * and parses the expression for that unit in a list of values.
 */
class TimeUnit {

  private static final Pattern STEP_REGEX = Pattern.compile("(\\*|\\d{1,2})\\/(\\d{1,2})");
  private static final Pattern RANGE_REGEX = Pattern.compile("(\\d{1,2})-(\\d{1,2})");
  private static final Pattern FIXED_NUMBER = Pattern.compile("^(\\d{1,2})$");

  private int low;
  private int high;

  public TimeUnit(int low, int high) {
    this.low = low;
    this.high = high;
  }

  public List<Integer> parseIntoValues(String expression) {
    String[] options = expression.split(",");
    Set<Integer> selected = new HashSet<>();
    for (String option : options) {

      //All options
      if ("*".equals(option)) {
        int start = low;
        while (start <= high) {
          selected.add(start);
          start++;
        }
        continue;
      }

      //Check for fixed value
      Matcher m = FIXED_NUMBER.matcher(option);
      if (m.matches()) {
        int value = Integer.parseInt(m.group(1));
        selected.add(value);
        continue;
      }

      //Check if the pattern is step
      m = STEP_REGEX.matcher(option);
      if (m.matches()) {
        String prefix = m.group(1);
        int step = 0;
        try {
          step = Integer.parseInt(m.group(2));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Step value should be number");
        }

        int start;
        if ("*".equals(prefix)) {
          start = low == 0 ? low : step;
        } else {
          try {
            start = Integer.parseInt(prefix);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("start should be either * or a number");
          }
        }

        while (start <= high) {
          selected.add(start);
          start += step;
        }

        continue;
      }

      //Check if the pattern is range
      m = RANGE_REGEX.matcher(option);
      if (m.matches()) {
        int start = Integer.parseInt(m.group(1));
        int end = Integer.parseInt(m.group(2));

        if (start < 0 || end < 0) {
          throw new IllegalArgumentException("Start & end should be positive number");
        }

        if (start < low) {
          throw new IllegalArgumentException("invalid start");
        }

        if (end > high) {
          throw new IllegalArgumentException("invalid end");
        }

        if (start > end) {
          throw new IllegalArgumentException("Range start should be less than end");
        }

        while (start <= end) {
          selected.add(start);
          start ++;
        }
        continue;
      }
    }

    List<Integer> values = new ArrayList<>(selected);
    Collections.sort(values);
    return values;
  }
}
