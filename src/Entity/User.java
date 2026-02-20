package Entity;
@Entity
@Table(name = "users")
@Data
public class User {


    @Id @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;



}
