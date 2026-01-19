package sb.schedule;

import lombok.RequiredArgsConstructor;
import sb.dto.CompanyDto;
import sb.dto.EmployeeDto;
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
        int monthShifts = 0;

        for (LocalDate day: HolidayChecker.getDays(month)) {
            if (HolidayChecker.isWeekend(day, verandaWork)) monthShifts += companyDto.getMaxEmployeePerDay();
            else monthShifts += companyDto.getMinEmployeePerDay();
        }

        return Math.round(
                (float) monthShifts / companyDto.getEmployees().size()
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

        List<EmployeeDto> employees = new ArrayList<>(companyDto.getEmployees());
        List<LocalDate> days = new ArrayList<>(employeeCountPerDay.keySet());
        days.sort(Comparator.naturalOrder());
        List<ScheduleDto> schedule = new ArrayList<>();

        for (LocalDate day: days) {
            String dayStrForSchedule = day.format(DateTimeFormatter.ofPattern("dd/MM"));
            List<EmployeeDto> employeePerDay = new ArrayList<>();
            Collections.shuffle(employees);

            for (EmployeeDto employee : employees) {
                if (canWork(employee, day)) {
                    employeePerDay.add(employee);
                    employee.setShiftsInARow(employee.getShiftsInARow() + 1);
                    employee.setWorkedToday(true);
                    employee.setMonthShifts(employee.getMonthShifts() - 1);
                } else {
                    employee.setShiftsInARow(0);
                    employee.setWorkedToday(false);
                }

                if (employeePerDay.size() == employeeCountPerDay.get(day)) {
                    schedule.add(new ScheduleDto(dayStrForSchedule, employeePerDay));
                    break;
                }
            }
        }
        return schedule;
    }

    private boolean canWork(EmployeeDto employee, LocalDate day) {
        if (employee.getMonthShifts() <= 0) return false;

        if (employee.getShiftsInARow() >= 3) return false;

        if (!employee.getWeekendDtoList().isEmpty()) {
            boolean isScheduledWeekend = employee.getWeekendDtoList().stream()
                    .anyMatch(weekendDto -> {
                        String dayOfMonthStr = day.format(DateTimeFormatter.ofPattern("dd"));
                        return weekendDto.isItWeekend(dayOfMonthStr);
                    });

            return !isScheduledWeekend;
        }
        return true;
    }
}
