package sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sb.dto.CompanyDto;
import sb.entity.Company;
import sb.exception.company.CompanyNameException;
import sb.exception.company.CompanyNotFoundException;
import sb.dto.model.CompanyModel;
import sb.repository.CompanyRepository;
import sb.utils.mapper.CompanyMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    public void create(CompanyModel model) {
        log.info("Adding Company with name - {}", model.getCompanyName());

        if (model.getCompanyName().isEmpty() ||
                companyRepository.existsByName(model.getCompanyName())) throw new CompanyNameException();

        companyRepository.save(mapper.modelToEntity(model));
    }

    public List<CompanyDto> getCompanyList() {
        log.info("Searching all Companies...");

        List<Company> companyList = companyRepository.findAll();

        if (companyList.isEmpty()) log.info("Company list is empty");
        else log.info("Company list found");

        return companyList.stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public void deleteById(String companyId) {
        log.info("Deleting Company with ID {}", companyId);

        companyRepository.delete(getById(companyId));

        log.info("Delete complete");
    }

    public void update(String companyId, CompanyModel model) {
        log.info("Updating Company with ID {}", companyId);

        Company company = getById(companyId);
        company.setName(model.getCompanyName());
        company.setMinEmployeePerDay(model.getMinEmployeePerDay());
        company.setMaxEmployeePerDay(model.getMaxEmployeePerDay());

        companyRepository.save(company);

        log.info("Update complete");
    }

    public Company getById(String companyId) {
        log.info("Searching Company with ID {}", companyId);

        return companyRepository.findByName(companyId)
                .orElseThrow(CompanyNotFoundException::new);
    }
}
