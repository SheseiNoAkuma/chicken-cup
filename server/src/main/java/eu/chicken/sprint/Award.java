package eu.chicken.sprint;

import eu.chicken.badge.Badge;
import eu.chicken.team.Teammate;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Award extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    @ManyToOne
    @NotNull
    public Teammate assignee;
    @ManyToOne
    @NotNull
    public Teammate assigner;
    @ManyToOne
    @NotNull
    public Badge badge;
}
