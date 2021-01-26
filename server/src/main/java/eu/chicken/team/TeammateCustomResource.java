package eu.chicken.team;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;

@Path("teammate")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class TeammateCustomResource {

    @GET
    @Path("/email/{email}")
    public Teammate getByName(@PathParam("email") String email) {
        return Teammate.findByEmail(email);
    }

}
