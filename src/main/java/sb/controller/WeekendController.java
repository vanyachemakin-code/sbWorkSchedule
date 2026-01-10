package sb.controller;

import sb.entity.Employee;
import lombok.RequiredArgsConstructor;
import sb.model.WeekendModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sb.service.EmployeeService;
import sb.service.WeekendService;

import java.text.MessageFormat;

@Controller
@RequestMapping("/work_schedule/api/sb/employee")
@RequiredArgsConstructor
public class WeekendController {

    private final WeekendService weekendService;
    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}/weekend-list")
    private String getEmployeeWeekendList(@PathVariable String employeeId, Model model) {
        model.addAttribute("weekendList", weekendService.getEmployeeWeekendList(employeeId));

        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employeeName", employee.getName());

        return "weekend/weekend-list";
    }

    @PostMapping("/{employeeId}/weekend/add")
    private String add(@PathVariable Long employeeId,
                       @ModelAttribute("weekend") WeekendModel model) {
        weekendService.create(model);

        return MessageFormat.format(
                "redirect:/work_schedule/api/sb/employee/{0}/weekend-list", employeeId
        );
    }

    @GetMapping("/{employeeId}/weekend/add")
    private String addForm(@PathVariable String employeeId, Model model) {
        model.addAttribute("weekend", new WeekendModel());

        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employeeName", employee.getName());

        return "weekend-form";
    }

    @PostMapping("/{employeeId}/weekend/{id}/delete")
    private String deleteById(@PathVariable Long employeeId,
                              @PathVariable Long id) {
        weekendService.deleteById(id);

        return MessageFormat.format(
                "redirect:/work_schedule/api/sb/employee/{0}/weekend-list", employeeId
        );
    }
}
