package sb.exception.employee;

public class EmployeeNameException extends RuntimeException {
    public EmployeeNameException() {
        super("Name is already taken!!!");
    }
}
