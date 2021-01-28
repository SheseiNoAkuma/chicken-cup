package eu.chicken.team;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class TeamService {

    public Team save(Team team) {
        if (team.members != null)
            team.members.forEach(m -> m.team = team);
        team.persistAndFlush();
        return team;
    }

    public Teammate getTeammateByEmail(String team, String email) {
        return Teammate.findByTeamAndEmail(team, email);
    }

    public List<Teammate> getByTeam(String team) {
        return Teammate.findByTeam(team);
    }

    public Team addTeammate(String id, Teammate member) {
        Team team = Team.findById(id);
        team.addMember(member);
        team.persistAndFlush();
        return team;
    }

}
