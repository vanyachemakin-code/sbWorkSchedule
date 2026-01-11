package sb.exception;

public class WeekendNotFoundException extends RuntimeException {
    public WeekendNotFoundException() {
        super("Weekend not found!!!");
    }
}
