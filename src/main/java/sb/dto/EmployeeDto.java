package sb.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDto {

    private String id;
    private String name;
    private List<WeekendDto> weekendDtoList = new ArrayList<>();
    private int shiftsInARow = 0;
    private int monthShifts;
}
