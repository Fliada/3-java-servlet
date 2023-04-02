package example.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_name", unique = true, updatable = false)
    private String name;
    @Column(name = "user_email", unique = true, updatable = false)
    private String email;
    @Column(name = "user_pass", updatable = false)
    private String pass;

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(long id, String name,String email,String pass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public UsersDataSet(String name,String email,String pass) {
        this.setId(-1);
        this.setName(name);
        this.setEmail(email);
        this.setPass(pass);
    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}