package Entity;
@Entity
@Table(name = "admins")
@Data
public class Admin {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

}
