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
@RequestMapping("/work_schedule/api")
@RequiredArgsConstructor
public class WeekendController {

    private final WeekendService weekendService;
    private final EmployeeService employeeService;

    @GetMapping("/{companyName}/employee/{employeeId}/weekend-list")
    private String getEmployeeWeekendList(@PathVariable String companyName,
                                          @PathVariable String employeeId, Model model) {
        model.addAttribute("weekendList", weekendService.getEmployeeWeekendList(employeeId));

        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employeeName", employee.getName());
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("companyName", companyName);

        return "weekend/weekend-list";
    }

    @PostMapping("/{companyName}/employee/{employeeId}/weekend/add")
    private String add(@PathVariable String companyName,
                       @PathVariable String employeeId,
                       @ModelAttribute("weekend") WeekendModel model) {
        weekendService.create(model);

        return MessageFormat.format(
                "redirect:/work_schedule/api/{0}/employee/{1}/weekend-list", companyName, employeeId
        );
    }

    @GetMapping("/{companyName}/employee/{employeeId}/weekend/add")
    private String addForm(@PathVariable String companyName,
                           @PathVariable String employeeId, Model model) {
        model.addAttribute("weekend", new WeekendModel());

        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employeeName", employee.getName());
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("companyName", companyName);

        return "weekend/weekend-form";
    }

    @PostMapping("/{companyName}/employee/{employeeId}/weekend/{id}/delete")
    private String deleteById(@PathVariable String companyName,
                              @PathVariable String employeeId,
                              @PathVariable Long id) {
        weekendService.deleteById(id);

        return MessageFormat.format(
                "redirect:/work_schedule/api/{0}/employee/{1}/weekend-list", companyName, employeeId
        );
    }
}
