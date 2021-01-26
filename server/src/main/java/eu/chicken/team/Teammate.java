package eu.chicken.team;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.NotFoundException;

@Entity
@Table(indexes = @Index(columnList = "email", unique = true))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Teammate extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    @NotNull(message = "name cannot be null")
    @Size(max = 200)
    public String name;
    @Size(max = 100)
    public String avatar;
    @NotNull(message = "email cannot be null")
    @Email
    @Size(max = 100)
    public String email;

    public static Teammate findByEmail(String email){
        return (Teammate) find("email", email).singleResultOptional()
                .orElseThrow(() ->  new NotFoundException("no teammate for given email"));
    }
}
