package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.dto.EmployeeDto;
import sb.entity.Employee;
import sb.model.EmployeeModel;
import sb.repository.CompanyRepository;

import java.util.UUID;

@Component
public record EmployeeMapper(CompanyRepository companyRepository, WeekendMapper weekendMapper) {

    public Employee modelToEntity(EmployeeModel model) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setName(model.getName());
        employee.setCompany(companyRepository.findById(model.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found!!!")));

        return employee;
    }

    public EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setWeekendDtoList(employee.getWeekends()
                .stream()
                .map(weekendMapper::entityToDto)
                .toList());

        return employeeDto;
    }

    public EmployeeModel entityToModel(Employee employee) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(employee.getId());
        employeeModel.setName(employee.getName());
        employeeModel.setCompanyId(employee.getCompany().getId());

        return employeeModel;
    }
}
