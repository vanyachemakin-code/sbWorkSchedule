package sb.exception.weekend;

public class WeekendNotFoundException extends RuntimeException {
    public WeekendNotFoundException() {
        super("Weekend not found!!!");
    }
}
