package mapper;

import dto.WeekendDto;
import entity.Weekend;
import model.WeekendModel;
import repository.EmployeeRepository;

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
