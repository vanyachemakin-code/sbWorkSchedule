package sb.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import sb.dto.EmployeeDto;

import java.util.List;

@Data
@AllArgsConstructor
public class ScheduleDto {

    private String date;
    private List<EmployeeDto> employees;
}
