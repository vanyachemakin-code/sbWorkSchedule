package sb.service;

import sb.dto.WeekendDto;
import sb.entity.Employee;
import sb.entity.Weekend;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sb.exception.weekend.WeekendDateException;
import sb.exception.weekend.WeekendNotFoundException;
import sb.utils.mapper.WeekendMapper;
import sb.dto.model.WeekendModel;
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
        log.info("Converting date format");

        String rawDate = model.getDate();
        if (rawDate != null && !rawDate.isEmpty()) {
            int dayOfMonth = Integer.parseInt(rawDate);
            String standardizedDate = String.format("%02d", dayOfMonth);
            model.setDate(standardizedDate);
        }

        log.info("Adding weekend for Employee with ID {}", model.getEmployeeId());

        if (model.getDate().isEmpty() ||
                weekendRepository.existsByDate(model.getDate())) throw new WeekendDateException();

        weekendRepository.save(mapper.modelToEntity(model));

        log.info("Add weekend complete");
    }

    public List<WeekendDto> getEmployeeWeekendList(String employeeId) {
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

        weekendRepository.delete(getById(id));

        log.info("Delete complete");
    }

    private Weekend getById(Long id) {
        log.info("Searching Weekend with ID {}", id);

        return weekendRepository.findById(id)
                .orElseThrow(WeekendNotFoundException::new);
    }
}
