package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.dto.CompanyDto;
import sb.entity.Company;

@Component
public record CompanyMapper(EmployeeMapper employeeMapper) {

    public CompanyDto entityToDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName(company.getName());
        companyDto.setMinEmployeePerDay(company.getMinEmployeePerDay());
        companyDto.setMaxEmployeePerDay(company.getMaxEmployeePerDay());
        companyDto.setEmployees(company.getEmployees()
                .stream()
                .map(employeeMapper::entityToDto)
                .toList());
        return companyDto;
    }
}
