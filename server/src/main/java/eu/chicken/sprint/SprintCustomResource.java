package eu.chicken.sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("sprint")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SprintCustomResource {

    @Inject
    SprintService sprintService;

    @GET
    @Path("/team/{team}/last")
    public Sprint getLastByTeam(@PathParam("team") String team) {
        return sprintService.findLastSprint(team);
    }

    @GET
    @Path("/team/{team}/")
    public List<Sprint> getByTeam(@PathParam("team") String team) {
        return sprintService.findByTeam(team);
    }

}
