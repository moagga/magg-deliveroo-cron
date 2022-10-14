package magg.deliveroo;

/**
 * Main class
 */
public class App {
    public static void main( String[] args ) {
        String expression = args[0];
        Cron cron = Cron.parse(expression);
        System.out.println("Minutes: " + cron.minutes());
        System.out.println("Hours: " + cron.hours());
        System.out.println("Days of Month: " + cron.daysOfMonth());
        System.out.println("Months: " + cron.months());
        System.out.println("Days of Week: " + cron.daysOfWeek());
        System.out.println("Command: " + cron.command());
    }
}
