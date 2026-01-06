package dto;

import lombok.Data;

@Data
public class WeekendDto {

    private Long id;
    private String date;
    private EmployeeDto employeeDto;
}
