package entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Weekend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
