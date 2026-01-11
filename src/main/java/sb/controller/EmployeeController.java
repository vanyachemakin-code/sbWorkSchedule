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

@Controller
@RequestMapping("/work_schedule/api/sb")
@RequiredArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final CompanyRepository companyRepository;

    @GetMapping("/employee-list")
    private String getEmployeeList(Model model) {
        model.addAttribute("employeeList", service.getAllEmployeesInCompany(getCompanyId("SB")));

        return "employee/employee-list";
    }

    @GetMapping("/employee/add")
    private String addForm(Model model) {
        model.addAttribute("employee", new EmployeeModel());

        return "employee/employee-form";
    }

    @PostMapping("/employee/add")
    private String add(@ModelAttribute("employee")EmployeeModel model) {
        model.setCompanyId(getCompanyId("SB"));
        service.create(model);

        return "redirect:/work_schedule/api/sb/employee-list";
    }

    @PostMapping("/employee/{id}/delete")
    private String deleteById(@PathVariable String id) {
        service.deleteById(id);

        return "redirect:/work_schedule/api/sb/employee-list";
    }

    @PostMapping("/employee/{id}/update")
    private String update(@PathVariable String id, EmployeeModel model) {
        service.update(getCompanyId("SB"), id, model);

        return "redirect:/work_schedule/api/sb/employee-list";
    }

    @GetMapping("/employee/{id}/update")
    private String updateForm(@PathVariable String id, Model model) {
        EmployeeModel employeeModel = mapper.entityToModel(service.getById(id));
        model.addAttribute("employee", employeeModel);

        return "employee/employee-form";
    }

    private String getCompanyId(String name) {
        return companyRepository.findByName(name)
                .orElseThrow(CompanyNotFoundException::new)
                .getId();
    }
}
