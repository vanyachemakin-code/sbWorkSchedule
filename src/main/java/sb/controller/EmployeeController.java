package sb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sb.exception.CompanyNotFoundException;
import sb.utils.mapper.EmployeeMapper;
import sb.model.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sb.repository.CompanyRepository;
import sb.service.EmployeeService;

import java.text.MessageFormat;

@Controller
@RequestMapping("/work_schedule/api")
@RequiredArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final CompanyRepository companyRepository;

    @GetMapping("/{companyName}/employee-list")
    private String getEmployeeList(@PathVariable String companyName, Model model) {
        model.addAttribute("employeeList", service.getAllEmployeesInCompany(getCompanyId(companyName)));
        model.addAttribute("companyName", companyName);

        return "employee/employee-list";
    }

    @GetMapping("/{companyName}/employee/add")
    private String addForm(@PathVariable String companyName, Model model) {
        model.addAttribute("employee", new EmployeeModel());
        model.addAttribute("companyName", companyName);
        return "employee/employee-form";
    }

    @PostMapping("/{companyName}/employee/add")
    private String add(@PathVariable String companyName,
                       @ModelAttribute("employee")EmployeeModel model) {
        model.setCompanyId(getCompanyId(companyName));
        service.create(model);

        return MessageFormat.format("redirect:/work_schedule/api/{0}/employee-list", companyName);
    }

    @PostMapping("/{companyName}/employee/{id}/delete")
    private String deleteById(@PathVariable String companyName,
                              @PathVariable String id) {
        service.deleteById(id);

        return MessageFormat.format("redirect:/work_schedule/api/{0}/employee-list", companyName);
    }

    @PostMapping("/{companyName}/employee/{id}/update")
    private String update(@PathVariable String companyName,
                          @PathVariable String id,
                          @ModelAttribute("employee") EmployeeModel model) {
        service.update(getCompanyId(companyName), id, model);

        return MessageFormat.format("redirect:/work_schedule/api/{0}/employee-list", companyName);
    }

    @GetMapping("/{companyName}/employee/{id}/update")
    private String updateForm(@PathVariable String companyName,
                              @PathVariable String id, Model model) {
        EmployeeModel employeeModel = mapper.entityToModel(service.getById(id));
        model.addAttribute("employee", employeeModel);
        model.addAttribute("companyName", companyName);

        return "employee/employee-form";
    }

    private String getCompanyId(String name) {
        return companyRepository.findByName(name.toUpperCase())
                .orElseThrow(CompanyNotFoundException::new)
                .getId();
    }
}
