package magg.deliveroo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CronTest {

  @Test
  public void testStar() {
    Cron cr  =Cron.parse("* * * * * command");
    Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7]", cr.daysOfWeek().toString());
  }

  @Test
  public void testRange() {
    //minutes
    Cron cr  =Cron.parse("1-4 * * * * command");
    Assertions.assertEquals("[1, 2, 3, 4]", cr.minutes().toString());

    //hours
    cr  =Cron.parse("* 2-12 * * * command");
    Assertions.assertEquals("[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]", cr.hours().toString());

    //Days of month
    cr  =Cron.parse("* * 3-3 * * command");
    Assertions.assertEquals("[3]", cr.daysOfMonth().toString());

    //Month
    cr  =Cron.parse("* * * 1-12 * command");
    Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]", cr.months().toString());

  }

  @ParameterizedTest
  @ValueSource(strings = {
      "* * * 0-7 *",
      "* * * 7-13 *",
      "* * * 4-2 *"
  })
  public void testRange_invalid_range(String expression) {
    Assertions.assertThrows(IllegalArgumentException.class, ()-> {
      Cron.parse(expression);
    });
  }

  @Test
  public void testStep() {
    //Minutes
    Cron cr  =Cron.parse("*/10 * * * * command");
    Assertions.assertEquals("[0, 10, 20, 30, 40, 50]", cr.minutes().toString());

    //Hours
    cr  =Cron.parse("* 1/4 * * * command");
    Assertions.assertEquals("[1, 5, 9, 13, 17, 21]", cr.hours().toString());

    //Day of month
    cr  =Cron.parse("* * */3 * * command");
    Assertions.assertEquals("[3, 6, 9, 12, 15, 18, 21, 24, 27, 30]", cr.daysOfMonth().toString());

    //Month
    cr  =Cron.parse("* * * */1 * command");
    Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]", cr.months().toString());

  }

  @Test
  public void testFixedValue() {
    //Minutes
    Cron cr  =Cron.parse("5,6,21 * * * * command");
    Assertions.assertEquals("[5, 6, 21]", cr.minutes().toString());
  }

  @Test
  public void testMultiple() {
    Cron cr  =Cron.parse("* 1-3,6-8,20/2 * * * command");
    Assertions.assertEquals("[1, 2, 3, 6, 7, 8, 20, 22]", cr.hours().toString());

    cr  =Cron.parse("* * * 3/6,9/11 * command");
    Assertions.assertEquals("[3, 9]", cr.months().toString());

    cr  =Cron.parse("* * * * *,1-2,5/1 command");
    Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7]", cr.daysOfWeek().toString());
  }
}
