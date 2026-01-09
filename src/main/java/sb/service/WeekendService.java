package sb.service;

import sb.dto.WeekendDto;
import sb.entity.Employee;
import sb.entity.Weekend;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sb.mapper.WeekendMapper;
import sb.model.WeekendModel;
import org.springframework.stereotype.Service;
import sb.repository.WeekendRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WeekendService {

    private final WeekendRepository weekendRepository;
    private final EmployeeService employeeService;
    private final WeekendMapper mapper;

    public void create(WeekendModel model) {
        log.info("Adding weekend for Employee with ID {}", model.getEmployeeId());

        weekendRepository.save(mapper.modelToEntity(model));

        log.info("Add weekend complete");
    }

    public List<WeekendDto> getEmployeeWeekendList(Long employeeId) {
        log.info("Search Weekends for Employee with ID {}", employeeId);

        Employee employee = employeeService.getById(employeeId);
        List<Weekend> weekends = employee.getWeekends();

        log.info("Weekends found");
        return weekends.stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting weekend by ID {}", id);

        hasWeekend(id);
        weekendRepository.deleteById(id);

        log.info("Delete complete");
    }

    private void hasWeekend(Long id) {
        weekendRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weekend not found!!!"));
    }
}
