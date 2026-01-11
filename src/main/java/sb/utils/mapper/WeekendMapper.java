package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.dto.WeekendDto;
import sb.entity.Weekend;
import sb.model.WeekendModel;
import sb.repository.EmployeeRepository;

@Component
public record WeekendMapper(EmployeeRepository employeeRepository) {

    public Weekend modelToEntity(WeekendModel model) {
        Weekend weekend = new Weekend();
        weekend.setDate(model.getDate());
        weekend.setEmployee(employeeRepository.findById(model.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found!!!")));

        return weekend;
    }

    public WeekendDto entityToDto(Weekend weekend) {
        WeekendDto weekendDto = new WeekendDto();
        weekendDto.setId(weekend.getId());
        weekendDto.setDate(weekend.getDate());

        return weekendDto;
    }
}
