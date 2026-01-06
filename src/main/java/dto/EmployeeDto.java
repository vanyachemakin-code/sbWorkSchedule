package dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private List<WeekendDto> weekendDtoList;
    private int shiftsInARow = 0;
    private int monthShifts;
}
