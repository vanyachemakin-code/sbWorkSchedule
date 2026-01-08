package controller;

import lombok.RequiredArgsConstructor;
import model.WeekendModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.WeekendService;

import java.text.MessageFormat;

@Controller
@RequestMapping("/work_schedule/api/sb/employee")
@RequiredArgsConstructor
public class WeekendController {

    private final WeekendService service;

    @GetMapping("/{employeeId}/weekend-list")
    private String getEmployeeWeekendList(@PathVariable Long employeeId, Model model) {
        model.addAttribute("weekend-list", service.getEmployeeWeekendList(employeeId));

        return "weekend/weekend-list";
    }

    @PostMapping("/{employeeId}/weekend/add")
    private String add(@PathVariable Long employeeId,
                       @ModelAttribute("weekend") WeekendModel model) {
        service.create(model);

        return MessageFormat.format(
                "redirect:/work_schedule/api/sb/employee/{0}/weekend-list", employeeId
        );
    }

    @GetMapping("/{employeeId}/weekend/add")
    private String addForm(@PathVariable Long employeeId, Model model) {
        model.addAttribute("weekend", new WeekendModel());

        return "weekend-form";
    }

    @PostMapping("/{employeeId}/weekend/{id}/delete")
    private String deleteById(@PathVariable Long employeeId,
                              @PathVariable Long id) {
        service.deleteById(id);

        return MessageFormat.format(
                "redirect:/work_schedule/api/sb/employee/{0}/weekend-list", employeeId
        );
    }
}
