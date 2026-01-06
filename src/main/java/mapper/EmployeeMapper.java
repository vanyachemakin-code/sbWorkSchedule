package mapper;

import dto.EmployeeDto;
import entity.Employee;
import model.EmployeeModel;
import repository.CompanyRepository;

public record EmployeeMapper(CompanyRepository companyRepository) {

    public Employee modelToEntity(EmployeeModel model) {
        Employee employee = new Employee();
        employee.setName(model.getName());
        employee.setCompany(companyRepository.findById(model.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found!!!")));

        return employee;
    }

    public EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());

        return employeeDto;
    }
}
