package eu.chicken.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    @NotNull
    @Size(max = 200)
    public String name;
    @Size(max = 100)
    public String icon;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @JsonProperty("members")
    List<Teammate> members = new ArrayList<>();

    public Team addMember(Teammate member) {
        member.team = this;
        members.add(member);
        return this;
    }
}
