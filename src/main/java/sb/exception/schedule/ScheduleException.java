package sb.exception.schedule;

public class ScheduleException extends RuntimeException {
    public ScheduleException() {
        super("Not Enough Employees!!!");
    }
}
