package eu.chicken.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Entity
@Table(indexes = @Index(columnList = "email", unique = true))
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Team team;

    public static Teammate findByTeamAndEmail(String team, String email){
        return (Teammate) find("team.id=?2 and email=?1", email, team).singleResultOptional()
                .orElseThrow(() ->  new NotFoundException(String.format("no teammate for given email <%s> in team <%s>", email, team)));
    }

    public static List<Teammate> findByTeam(String team){
        return list("team.id", team);
    }
}
