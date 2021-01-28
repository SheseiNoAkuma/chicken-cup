package eu.chicken.sprint;

import eu.chicken.team.Team;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Sprint extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    @NotNull
    @Size(max = 200)
    public String name;
    @ManyToOne
    @NotNull
    public Team team;
    @NotNull
    public LocalDate starting;
    @NotNull
    @FutureOrPresent
    public LocalDate ending;
    @OneToMany
    @Singular
    List<Award> awards;

    public static Sprint findLastSprint(String team) {
        return (Sprint) list("team.id", Sort.by("name"), team).stream().findFirst()
                .orElseThrow(() ->  new NotFoundException("no sprint found for giben team"));
    }
    public static List<Sprint> findByTeam(String team) {
        return find("team.id", team).stream().map(e -> (Sprint) e).collect(Collectors.toList());
    }

}
