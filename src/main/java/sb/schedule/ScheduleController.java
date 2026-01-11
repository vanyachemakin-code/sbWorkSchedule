package sb.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sb.entity.Company;
import sb.exception.CompanyNotFoundException;
import sb.repository.CompanyRepository;
import sb.utils.mapper.CompanyMapper;

import java.text.MessageFormat;
import java.time.Month;
import java.util.List;

@Controller
@RequestMapping("/work_schedule/api")
@RequiredArgsConstructor
@Log4j2
public class ScheduleController {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @PostMapping("/{companyName}/schedule/generate")
    private String getSchedule(@PathVariable String companyName,
                               @ModelAttribute("scheduleModel") ScheduleModel scheduleModel,
                               RedirectAttributes redirectAttributes) {
        log.info("Searching Company with name {}", companyName.toUpperCase());

        Company company = companyRepository.findByName(companyName.toUpperCase())
                .orElseThrow(CompanyNotFoundException::new);

        log.info("Company found");
        log.info("Getting Schedule data");

        ScheduleGenerator scheduleGenerator = new ScheduleGenerator(
                Month.valueOf(scheduleModel.getMonth()
                        .toUpperCase()),
                companyMapper.entityToDto(company),
                scheduleModel.isVerandaWork());

        log.info("Schedule data is: Month - {}, Company - {}, is Veranda work - {}",
                scheduleModel.getMonth().toUpperCase(),
                company.getName(),
                scheduleModel.isVerandaWork());

        log.info("Trying generate Work Schedule");

        List<ScheduleDto> generatedSchedule = scheduleGenerator.generate();
        redirectAttributes.addFlashAttribute("scheduleList", generatedSchedule);

        log.info("Work Schedule was generated");

        return MessageFormat.format("redirect:/work_schedule/api/{0}/schedule-view", companyName);
    }

    @GetMapping("/{companyName}/schedule/generate")
    private String getScheduleForm(@PathVariable String companyName, Model model) {
        model.addAttribute("scheduleModel", new ScheduleModel());
        model.addAttribute("companyName", companyName);
        return "schedule/schedule-form";
    }

    @GetMapping("{companyName}/schedule-view")
    private String getScheduleView(@PathVariable String companyName, Model model) {
        model.addAttribute("companyName", companyName);
        return "schedule/schedule-view";
    }
}
