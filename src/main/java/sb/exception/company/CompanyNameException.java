package sb.exception.company;

public class CompanyNameException extends RuntimeException {
    public CompanyNameException() {
        super("Name is already taken!!!");
    }
}
