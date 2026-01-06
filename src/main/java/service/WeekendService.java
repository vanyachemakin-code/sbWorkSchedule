package service;

import dto.WeekendDto;
import entity.Employee;
import entity.Weekend;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mapper.WeekendMapper;
import model.WeekendModel;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;
import repository.WeekendRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WeekendService {

    private final WeekendRepository weekendRepository;
    private final EmployeeRepository employeeRepository;
    private final WeekendMapper mapper;

    public void create(WeekendModel model) {
        log.info("Adding weekend for Employee with ID {}", model.getEmployeeId());

        weekendRepository.save(mapper.modelToEntity(model));

        log.info("Add weekend complete");
    }

    public List<WeekendDto> getAllWeekendsForEmployee(Long employeeId) {
        log.info("Search Weekends for Employee with ID {}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found!!!"));
        List<Weekend> weekends = employee.getWeekends();

        log.info("Weekends found");
        return weekends.stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting weekend by ID {}", id);

        if (weekendRepository.findById(id).isEmpty()) throw new RuntimeException("Weekend not found!!!");
        weekendRepository.deleteById(id);

        log.info("Delete complete");
    }
}
