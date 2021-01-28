package eu.chicken.team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("team")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class TeamCustomResource {

    @Inject
    TeamService service;

    @POST
    @Path("/")
    public Response save(Team team) {
        return Response.status(Response.Status.CREATED).entity(service.save(team)).build();
    }

    @GET
    @Path("/{team}/teammate/email/{email}")
    public Teammate getTeammateByEmail(@PathParam("team") String team, @PathParam("email") String email) {
        return service.getTeammateByEmail(team, email);
    }

    @GET
    @Path("/{team}/teammate/")
    public List<Teammate> getByTeam(@PathParam("team") String team) {
        return service.getByTeam(team);
    }

    @POST
    @Path("/{team}/teammate/")
    public Team addTeammate(@PathParam("team") String id, Teammate member) {
        return service.addTeammate(id, member);
    }
}
