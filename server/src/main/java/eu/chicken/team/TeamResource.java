package eu.chicken.team;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;

public interface TeamResource extends PanacheEntityResource<Team, String> {

    @Override
    @MethodProperties(exposed = false)
    Team add(Team team);

    @Override
    @MethodProperties(exposed = false)
    Team update(String s, Team team);
}
