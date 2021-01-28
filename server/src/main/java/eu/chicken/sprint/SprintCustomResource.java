package eu.chicken.sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import java.util.List;

@Path("sprint")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SprintCustomResource {

    @GET
    @Path("/team/{team}/last")
    public Sprint getByName(@PathParam("team") String team) {
        return Sprint.findLastSprint(team);
    }

    @GET
    @Path("/team/{team}/")
    public List<Sprint> getByTeam(@PathParam("team") String team) {
        return Sprint.findByTeam(team);
    }

}
