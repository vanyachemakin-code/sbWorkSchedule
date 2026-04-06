package sb.exception.weekend;

public class WeekendDateException extends RuntimeException {
    public WeekendDateException() {
        super("Date is already taken!!!");
    }
}
