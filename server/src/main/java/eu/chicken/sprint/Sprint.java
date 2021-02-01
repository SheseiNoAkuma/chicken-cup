package eu.chicken.sprint;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Objects;
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
    @JsonIgnore
    public Team team;
    @Transient
    public String teamRef;
    @NotNull
    public LocalDate starting;
    @NotNull
    @FutureOrPresent
    public LocalDate ending;
    @OneToMany
    @Singular
    List<Award> awards;

    public void setTeam(Team team) {
        this.team = team;
        this.teamRef = team.id;
    }

    public void setTeamRef(String teamRef) {
        this.teamRef = teamRef;
        this.team = Team.builder().id(teamRef).build();
    }

    public String getTeamRef() {
        return Objects.isNull(team) ? teamRef : team.id;
    }

    public static Sprint findLastSprint(String team) {
        return (Sprint) list("team.id", Sort.by("starting", Sort.Direction.Descending), team).stream().findFirst()
                .orElseThrow(() ->  new NotFoundException("no sprint found for given team"));
    }
    public static List<Sprint> findByTeam(String team) {
        return find("team.id", team).stream().map(Sprint.class::cast).collect(Collectors.toList());
    }

}
