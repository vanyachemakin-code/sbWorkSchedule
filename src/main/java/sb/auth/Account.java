package sb.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
