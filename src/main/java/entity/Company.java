package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name = "SB";

    @Column(name = "min_employee_per_day")
    private int minEmployeePerDay = 3;

    @Column(name = "max_employee_per_day")
    private int maxEmployeePerDay = 4;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;
}
