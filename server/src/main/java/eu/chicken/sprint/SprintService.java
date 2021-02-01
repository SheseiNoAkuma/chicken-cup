package eu.chicken.sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class SprintService {

    public Sprint findLastSprint(String team){
        return Sprint.findLastSprint(team);
    }

    public List<Sprint> findByTeam(String team) {
        return Sprint.findByTeam(team);
    }
}
