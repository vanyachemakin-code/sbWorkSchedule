package sb.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sb.dto.CompanyDto;
import sb.dto.EmployeeDto;
import sb.exception.EmployeeNotFoundException;
import sb.exception.WeekendNotFoundException;
import sb.utils.HolidayChecker;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
public class ScheduleGenerator {


    private final Map<LocalDate, Integer> employeeCountPerDay = new HashMap<>();
    private final Month month;
    private final CompanyDto companyDto;
    private final boolean verandaWork;

    private int getEmployeeMonthShifts() {
        return Math.round(
                (float) (HolidayChecker.getDaysInMonth(month) * companyDto.getMaxEmployeePerDay())
                        / companyDto.getEmployees().size()
        );
    }

    private void setEmployeeMonthShifts() {
        Collection<EmployeeDto> employees = companyDto.getEmployees();
        for (EmployeeDto employee: employees) employee.setMonthShifts(getEmployeeMonthShifts());
    }

    private void setEmployeeCountPerDay() {
        for (LocalDate day : HolidayChecker.getDays(month)) {
            if (HolidayChecker.isWeekend(day, verandaWork)) employeeCountPerDay.put(day, companyDto.getMaxEmployeePerDay());
            else employeeCountPerDay.put(day, companyDto.getMinEmployeePerDay());
        }
    }

    public List<ScheduleDto> generate() {
        setEmployeeMonthShifts();
        setEmployeeCountPerDay();
        List<EmployeeDto> employees = companyDto.getEmployees();
        Set<LocalDate> days = employeeCountPerDay.keySet();
        List<ScheduleDto> schedule = new ArrayList<>();

        point:
        for (LocalDate day: days) {
            String dayStr = day.format(DateTimeFormatter.ofPattern("dd/MM"));
            List<EmployeeDto> employeePerDay = new ArrayList<>();
            for (EmployeeDto employee: employees) {
                if (canWork(employee, dayStr)) {
                    employeePerDay.add(employee);
                    employee.setShiftsInARow(employee.getShiftsInARow() + 1);
                    employee.setMonthShifts(employee.getMonthShifts() - 1);
                }
                if (employee.getShiftsInARow() == 4) employee.setShiftsInARow(0);

                if (employeePerDay.size() == employeeCountPerDay.get(day)) {
                    schedule.add(
                            new ScheduleDto(dayStr, employeePerDay)
                    );
                    continue point;
                }
            }
        }
        return schedule;
    }

    private boolean canWork(EmployeeDto employee, String day) {
        if (employee.getWeekendDtoList().isEmpty()) return employee.getShiftsInARow() < 3 &&
                employee.getMonthShifts() > 0;

        return employee.getShiftsInARow() < 3 &&
                employee.getMonthShifts() > 0 &&
                !employee.getWeekendDtoList()
                        .stream()
                        .filter(weekendDto -> weekendDto.getDate().equals(day))
                        .findFirst()
                        .orElseThrow(WeekendNotFoundException::new)
                        .isItWeekend(day);
    }
}
