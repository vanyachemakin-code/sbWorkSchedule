package sb.dto;

import lombok.Data;

@Data
public class WeekendDto {

    private Long id;
    private String date;
    private EmployeeDto employeeDto;

    public boolean isItWeekend(String day) {
        return date.equals(day);
    }
}
