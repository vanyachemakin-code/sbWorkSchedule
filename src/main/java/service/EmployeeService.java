package service;

import dto.EmployeeDto;
import entity.Company;
import entity.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mapper.EmployeeMapper;
import model.EmployeeModel;
import org.springframework.stereotype.Service;
import repository.CompanyRepository;
import repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeMapper mapper;

    public void create(EmployeeModel model) {
        log.info("Adding Employee with name - {}", model.getName());

        employeeRepository.save(mapper.modelToEntity(model));

        log.info("Add Employee complete");
    }

    public List<EmployeeDto> getAllEmployeesInCompany(Long companyId) {
        log.info("Search all Employees in Compony with ID {}", companyId);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found!!!"));
        List<Employee> employees = company.getEmployees();

        log.info("Employees found");
        return employees.stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting Employee with ID {}", id);

        getById(id);
        employeeRepository.deleteById(id);

        log.info("Delete complete");
    }

    public void update(Long companyId, Long employeeId, EmployeeModel model) {
        log.info("Updating Employee with ID {} in Company with ID {}", employeeId, companyId);

        Employee employee = getById(employeeId);
        employee.setName(model.getName());
        employeeRepository.save(employee);

        log.info("Update complete");
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found!!!"));
    }
}
