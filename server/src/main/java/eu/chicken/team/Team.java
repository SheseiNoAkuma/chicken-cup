package eu.chicken.team;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Team extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    @NotNull(message = "name cannot be null")
    @Size(max = 200)
    public String name;
    @Size(max = 100)
    public String icon;
    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    List<Teammate> members;
}
