package sb.utils.mapper;

import org.springframework.stereotype.Component;
import sb.dto.CompanyDto;
import sb.entity.Company;
import sb.dto.model.CompanyModel;

import java.util.UUID;

@Component
public record CompanyMapper(EmployeeMapper employeeMapper) {

    public CompanyDto entityToDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        company.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setMinEmployeePerDay(company.getMinEmployeePerDay());
        companyDto.setMaxEmployeePerDay(company.getMaxEmployeePerDay());
        companyDto.setEmployees(company.getEmployees()
                .stream()
                .map(employeeMapper::entityToDto)
                .toList());

        return companyDto;
    }

    public Company modelToEntity(CompanyModel model) {
        Company company = new Company();
        company.setId(UUID.randomUUID().toString());
        company.setName(model.getCompanyName());
        company.setMinEmployeePerDay(model.getMinEmployeePerDay());
        company.setMaxEmployeePerDay(model.getMaxEmployeePerDay());

        return company;
    }

    public CompanyModel entityToModel(Company company) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setCompanyName(company.getName());
        companyModel.setMinEmployeePerDay(company.getMinEmployeePerDay());
        companyModel.setMaxEmployeePerDay(company.getMaxEmployeePerDay());

        return companyModel;
    }
}
