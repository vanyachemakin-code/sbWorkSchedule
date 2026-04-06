package sb.dto.model;

import lombok.Data;

@Data
public class CompanyModel {

    private String companyName;
    private int minEmployeePerDay;
    private int maxEmployeePerDay;
}
