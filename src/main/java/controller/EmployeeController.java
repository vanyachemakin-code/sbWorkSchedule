package controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mapper.EmployeeMapper;
import model.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.CompanyRepository;
import service.EmployeeService;

@Controller
@RequestMapping("/work_schedule/api/sb")
@RequiredArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final CompanyRepository companyRepository;
    private final Long sbCompanyIndex = companyRepository.findAll()
            .stream()
            .filter(company -> company.getName().equals("SB"))
            .findFirst()
            .orElseThrow().getId();

    @GetMapping("/employee-list")
    private String getEmployeeList(Model model) {
        model.addAttribute("employee-list", service.getAllEmployeesInCompany(sbCompanyIndex));

        return "employee/employee-list";
    }

    @GetMapping("/employee/add")
    private String addForm(Model model) {
        model.addAttribute("employee", new EmployeeModel());

        return "employee/employee-form";
    }

    @PostMapping("/employee/add")
    private String add(@ModelAttribute("employee")EmployeeModel model) {
        service.create(model);

        return "redirect:/work_schedule/api/sb/employee-list";
    }

    @PostMapping("/employee/{id}/delete")
    private String deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return "redirect:/sb/work_schedule/api/sb/employee-list";
    }

    @PostMapping("/employee/{id}/update")
    private String update(@PathVariable Long id, EmployeeModel model) {
        service.update(sbCompanyIndex, id, model);

        return "redirect:/sb/work_schedule/api/sb/employee-list";
    }

    @GetMapping("/employee/{id}/update")
    private String updateForm(@PathVariable Long id, Model model) {
        EmployeeModel employeeModel = mapper.entityToModel(service.getById(id));
        model.addAttribute("employee", employeeModel);

        return "employee/employee-form";
    }
}
